package com.example.meta_habit.ui.screen.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meta_habit.data.data_store.PreferencesDataStoreImp
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.data.repository.HabitRepository
import com.example.meta_habit.ui.utils.FilterType
import com.example.meta_habit.ui.utils.getDayOfWeekDayMonthMontNameSimple
import com.example.meta_habit.ui.utils.getRepeatType
import com.example.meta_habit.ui.utils.isValidateDateThreeDaysReminder
import com.example.meta_habit.ui.utils.isValidateDateTodayReminder
import com.example.meta_habit.ui.utils.isValidateDateWeekReminder
import com.example.meta_habit.ui.utils.toLocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
): ViewModel() {

    private val _selectedHabit =  MutableStateFlow<HabitWithTasks?>(null)

    private val _listOfHabit = MutableStateFlow<List<HabitWithTasks>>(emptyList())

    private val _listFilterOfHabit = MutableStateFlow<List<HabitWithTasks>>(emptyList())
    val listOfHabit = _listFilterOfHabit.asStateFlow()

    private val _selectedFilter = MutableStateFlow(FilterTypeAndLabel(filterType = FilterType.TODAY))
    val selectedFilter = _selectedFilter.asStateFlow()

    init {

        viewModelScope.launch {

            preferencesDataStore.save(FilterType.TODAY.ordinal)
            launch {

                preferencesDataStore.getInt().collect{
                    Log.d("PREFERENCE_FILTER", "filterType :: $it")
                    onSelectFilter(FilterType.entries[it?:0])

                    when(FilterType.entries[it?:0]){
                        FilterType.TODAY -> {
                            val listFilter = _listOfHabit.value.filter { currentHabit ->
                                isValidateDateTodayReminder(
                                    baseDate = (currentHabit.habit.dateReminder?:0).toLocalDate(),
                                    type = getRepeatType(currentHabit.habit.repetition?:0)
                                )
                            }
                            _listFilterOfHabit.value = listFilter
                        }
                        FilterType.WEEK -> {
                            val listFilter = _listOfHabit.value.filter { currentHabit ->
                                isValidateDateWeekReminder(
                                    date = (currentHabit.habit.dateReminder?:0).toLocalDate(),
                                    type = getRepeatType(currentHabit.habit.repetition?:0)
                                )
                            }
                            _listFilterOfHabit.value = listFilter
                        }
                        FilterType.TREE_DAYS -> {
                            val listFilter = _listOfHabit.value.filter { currentHabits ->
                                isValidateDateThreeDaysReminder(
                                    date = (currentHabits.habit.dateReminder?:0).toLocalDate(),
                                    type = getRepeatType(currentHabits.habit.repetition?:0)
                                )
                            }
                            _listFilterOfHabit.value = listFilter
                        }

                        FilterType.ALL ->{
                            _listFilterOfHabit.value = _listOfHabit.value
                        }
                    }
                }

                val updateFilterLabel = _selectedFilter.value.copy(
                    label = Date().getDayOfWeekDayMonthMontNameSimple()
                )
                _selectedFilter.value = updateFilterLabel
            }

            launch {

                habitRepository.getListOfHabitWithTasks().collect{ habitTask ->
                    _listOfHabit.value = habitTask
                        val dateReminder = habitTask.map { it.habit.dateReminder }
                        println("dateReminder :: $dateReminder")
                    _listFilterOfHabit.value = habitTask
                    _selectedHabit.value?.let { currentSelected ->
                        _listOfHabit.value.find { it.habit.id == currentSelected.habit.id }?.let { updatedHabit ->
                            _selectedHabit.value = updatedHabit
                        }
                    }
                }
            }
        }
    }

    fun onSelectNote(habit : HabitWithTasks){
        _selectedHabit.value = habit
        habitRepository.setSelectedHabit(habit.habit)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSelectFilter(filterType: FilterType){
        viewModelScope.launch {
            preferencesDataStore.save(filterType.ordinal)
        }

        val label: String = when(filterType){
            FilterType.TODAY -> {
                Date().getDayOfWeekDayMonthMontNameSimple()
            }
            FilterType.WEEK -> {
                "Semanal"
            }
            FilterType.TREE_DAYS -> {
                "Proximos 3 dias"
            }

            FilterType.ALL ->{
                "Todas"
            }
        }

        _selectedFilter.value = FilterTypeAndLabel(filterType = filterType, label = label)
    }

    fun onDeleteNote(){
        viewModelScope.launch {
            _selectedHabit.value?.let { habit ->
                //val deleteDeferred = async(Dispatchers.IO) { habitRepository.deleteHabit(habit)  }

                //val resultDelete = deleteDeferred.await()

                val listCopy = _listOfHabit.value.toMutableList()
                listCopy.remove(habit)

                _listOfHabit.value = listCopy
            }
        }
    }

    fun onConfirmDeleteHabit(){}

    fun onRevertedDeleteHabit(){}

    fun onPin(){
        viewModelScope.launch {
            _selectedHabit.value?.let { currentSelectedHabitTask ->

                val updatedHabitEntity = currentSelectedHabitTask.habit.copy(
                    isPinned = currentSelectedHabitTask.habit.isPinned?.not() ?: false
                )

                val pinDeferred = async(Dispatchers.IO) { habitRepository.updateHabitToPint(updatedHabitEntity) }

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