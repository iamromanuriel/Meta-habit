package com.example.meta_habit.data.repository

import androidx.room.util.appendPlaceholders
import com.example.meta_habit.data.db.AppDatabase
import com.example.meta_habit.data.db.entity.HabitEntity

class HabitRepository(
    private val appDatabase: AppDatabase
) {

    suspend fun onSaveHabit(){
        val habitEntity = HabitEntity(
            title = "title",
            repetition = 1,
            hasReminder = false,
            dateReminder = 1,
            tag = 1,
            color = 1,
            dateCreate = 1,
            dateUpdate = 1
        )
        appDatabase.habitDao().insert(habitEntity)
    }
}