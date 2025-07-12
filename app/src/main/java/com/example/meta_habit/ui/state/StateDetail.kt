package com.example.meta_habit.ui.state

import com.example.meta_habit.data.db.entity.HabitWithTasks
import java.util.Date

sealed class ActionDeleteHabit{
    data class Fail(val message: String): ActionDeleteHabit()
    object Success: ActionDeleteHabit()
    object Await: ActionDeleteHabit()
}

data class HabitScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val habit: HabitWithTasks? = null,
    val listDaysChecked: List<DayIsChecked> = emptyList(),
)

data class DayIsChecked(
    val date: Date,
    val isChecked: Boolean = false
)