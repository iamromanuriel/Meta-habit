package com.example.meta_habit.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.data.repository.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val habitRepository: HabitRepository
): ViewModel() {

    private val _selectedHabit =  MutableStateFlow<HabitWithTasks?>(null)

    private val _listOfHabit = MutableStateFlow<List<HabitWithTasks>>(emptyList())
    val listOfHabit = _listOfHabit.asStateFlow()

    init {
        println("onInitViewModelHome ::")
        viewModelScope.launch {
            launch {
                habitRepository.getListOfHabitWithTasks().collect{ habitTask ->
                    _listOfHabit.value = habitTask
                }
            }
        }
    }

    fun onSelectNote(habit : HabitWithTasks){
        _selectedHabit.value = habit
        habitRepository.setSelectedHabit(habit.habit)
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
            _selectedHabit.value?.let { habit ->
                val pinDeferred = async(Dispatchers.IO) { habitRepository.updateHabitToPint(habit.habit)  }

                val resultPin = pinDeferred.await()
            }
        }
    }
}