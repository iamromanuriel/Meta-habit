package com.example.meta_habit.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.data.repository.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val habitRepository: HabitRepository
): ViewModel() {

    private val _selectedHabit =  MutableStateFlow<HabitEntity?>(null)

    private val _listOfHabit = MutableStateFlow<List<HabitEntity>>(emptyList())
    val listOfHabit = _listOfHabit.asStateFlow()

    init {
        println("onInitViewModelHome ::")
        viewModelScope.launch {
            launch{
                habitRepository.getLIsListOfHabit().collect{ habits ->
                    _listOfHabit.value = habits
                }
            }
        }
    }

    fun onSelectNote(habit : HabitEntity){
        _selectedHabit.value = habit
    }

    fun onDeleteNote(){
        viewModelScope.launch {
            _selectedHabit.value?.let { habit ->
                val deleteDeferred = async(Dispatchers.IO) { habitRepository.deleteHabit(habit)  }

                val resultDelete = deleteDeferred.await()
            }
        }
    }

    fun onPin(){
        viewModelScope.launch {
            _selectedHabit.value?.let { habit ->
                val pinDeferred = async(Dispatchers.IO) { habitRepository.updateHabitToPint(habit)  }

                val resultPin = pinDeferred.await()
            }
        }
    }
}