package com.example.meta_habit.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meta_habit.data.repository.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val habitRepository: HabitRepository
): ViewModel() {

    init {
        println("onInitViewModelHome ::")
        viewModelScope.launch {
            launch(Dispatchers.IO){
                habitRepository.onSaveHabit()
            }
        }
    }
}