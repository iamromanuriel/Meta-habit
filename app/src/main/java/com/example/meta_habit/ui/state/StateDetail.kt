package com.example.meta_habit.ui.state

import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.data.db.entity.HabitWithTasks

data class HabitScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val habit: HabitWithTasks? = null
)