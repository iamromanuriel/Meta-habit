package com.example.meta_habit.ui.screen.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meta_habit.data.data_store.PreferencesDataStoreImp
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.data.repository.HabitRepository
import com.example.meta_habit.ui.state.ActionDeleteHabit
import com.example.meta_habit.ui.utils.FilterType
import com.example.meta_habit.ui.utils.getDayOfWeekDayMonthMontNameSimple
import com.example.meta_habit.ui.utils.getRepeatType
import com.example.meta_habit.ui.utils.isValidateDateThreeDaysReminder
import com.example.meta_habit.ui.utils.isValidateDateTodayReminder
import com.example.meta_habit.ui.utils.isValidateDateWeekReminder
import com.example.meta_habit.ui.utils.toLocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Date

data class FilterTypeAndLabel(
    val filterType: FilterType,
    val label: String? = null
)

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(
    private val habitRepository: HabitRepository,
    private val preferencesDataStore: PreferencesDataStoreImp
) : ViewModel() {

    private val _delete = Channel<ActionDeleteHabit>()
    val delete = _delete.receiveAsFlow()

    private val _selectedHabit = MutableStateFlow<HabitWithTasks?>(null)

    private val _listOfHabit = MutableStateFlow<List<HabitWithTasks>>(emptyList())
    val listOfHabit = _listOfHabit.asStateFlow()

    private val _selectedFilter =
        MutableStateFlow(FilterTypeAndLabel(filterType = FilterType.TODAY))
    val selectedFilter = _selectedFilter.asStateFlow()

    init {

        viewModelScope.launch {

            preferencesDataStore.save(FilterType.TODAY.ordinal)
            launch {

                preferencesDataStore.getInt().collect {
                    onSelectFilter(FilterType.entries[it ?: 0])
                }

                val updateFilterLabel = _selectedFilter.value.copy(
                    label = Date().getDayOfWeekDayMonthMontNameSimple()
                )
                _selectedFilter.value = updateFilterLabel
            }

            launch {
                    preferencesDataStore.getInt().combine(habitRepository.getListOfHabitWithTasks())
                    { type, list ->
                        {
                            val habits = when (FilterType.entries[type ?: 0]) {
                                FilterType.TODAY -> {
                                    list.filter { currentHabit ->
                                        isValidateDateTodayReminder(
                                            baseDate = (currentHabit.habit.dateReminder
                                                ?: 0).toLocalDate(),
                                            type = getRepeatType(currentHabit.habit.repetition ?: 0)
                                        )
                                    }
                                }

                                FilterType.WEEK -> {
                                    list.filter { currentHabit ->
                                        isValidateDateWeekReminder(
                                            date = (currentHabit.habit.dateReminder
                                                ?: 0).toLocalDate(),
                                            type = getRepeatType(currentHabit.habit.repetition ?: 0)
                                        )
                                    }
                                }

                                FilterType.TREE_DAYS -> {
                                    list.filter { currentHabits ->
                                        isValidateDateThreeDaysReminder(
                                            date = (currentHabits.habit.dateReminder
                                                ?: 0).toLocalDate(),
                                            type = getRepeatType(
                                                currentHabits.habit.repetition ?: 0
                                            )
                                        )
                                    }
                                }

                                FilterType.ALL -> {
                                    list
                                }
                            }

                            habits
                        }
                    }.collect { filterType ->
                        _listOfHabit.value = filterType.invoke()
                    }

            }
        }
    }

    fun onSelectNote(habit: HabitWithTasks) {
        _selectedHabit.value = habit
        habitRepository.setSelectedHabit(habit.habit)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSelectFilter(filterType: FilterType) {
        viewModelScope.launch {
            preferencesDataStore.save(filterType.ordinal)
        }

        val label: String = when (filterType) {
            FilterType.TODAY -> {
                Date().getDayOfWeekDayMonthMontNameSimple()
            }

            FilterType.WEEK -> {
                "Semanal"
            }

            FilterType.TREE_DAYS -> {
                "Proximos 3 dias"
            }

            FilterType.ALL -> {
                "Todas"
            }
        }

        _selectedFilter.value = FilterTypeAndLabel(filterType = filterType, label = label)
    }

    fun onDeleteNote() {
        viewModelScope.launch {
            _selectedHabit.value?.let { habit ->
                val deleteDeferred = async(Dispatchers.IO) { habitRepository.deleteHabit() }
                val resultDelete = deleteDeferred.await()
                if(resultDelete.isSuccess){
                    _delete.send(ActionDeleteHabit.Success)
                }else{
                    _delete.send(ActionDeleteHabit.Fail(resultDelete.exceptionOrNull()?.message?:""))
                }
            }
        }
    }

    fun onConfirmDeleteHabit() {}

    fun onRevertedDeleteHabit() {}

    fun onPin() {
        viewModelScope.launch {
            _selectedHabit.value?.let { currentSelectedHabitTask ->

                val updatedHabitEntity = currentSelectedHabitTask.habit.copy(
                    isPinned = currentSelectedHabitTask.habit.isPinned?.not() ?: false
                )

                val pinDeferred =
                    async(Dispatchers.IO) { habitRepository.updateHabitToPint(updatedHabitEntity) }

                val resultPin = pinDeferred.await()

                resultPin.onSuccess {
                    _selectedHabit.value = currentSelectedHabitTask.copy(
                        habit = updatedHabitEntity
                    )
                }.onFailure { e ->
                    Log.e("HomeViewModel", "Error al anclar/desanclar hábito: ${e.message}")
                }
            }
        }
    }
}