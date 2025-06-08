package com.example.meta_habit.data.repository

import com.example.meta_habit.data.db.AppDatabase

class MainRepository(
    private val appDatabase: AppDatabase
) {

    suspend fun onReCreateTaskHabitRepeat(){
        val allHabit = appDatabase.habitDao().getAllHabitRepeat()


    }
}