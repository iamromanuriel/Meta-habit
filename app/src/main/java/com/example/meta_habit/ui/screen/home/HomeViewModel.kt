package com.example.meta_habit.ui.screen.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.data.repository.HabitRepository
import com.example.meta_habit.ui.utils.FilterType
import com.example.meta_habit.ui.utils.getDayNumMonthFromDate
import com.example.meta_habit.ui.utils.getNameMouthSpanish
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

class HomeViewModel(
    private val habitRepository: HabitRepository
): ViewModel() {

    private val _selectedHabit =  MutableStateFlow<HabitWithTasks?>(null)

    private val _listOfHabit = MutableStateFlow<List<HabitWithTasks>>(emptyList())
    val listOfHabit = _listOfHabit.asStateFlow()

    private val _selectedFilter = MutableStateFlow(FilterTypeAndLabel(filterType = FilterType.TODAY))
    val selectedFilter = _selectedFilter.asStateFlow()

    init {
        println("onInitViewModelHome ::")
        viewModelScope.launch {
            launch {
                habitRepository.getListOfHabitWithTasks().collect{ habitTask ->
                    _listOfHabit.value = habitTask
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
        val label: String = when(filterType){
            FilterType.TODAY -> {
                Date().getNameMouthSpanish()
            }
            FilterType.WEEK -> {
                "Semana"
            }
            FilterType.TREE_DAYS -> {
                "Proximos 3 dias"
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