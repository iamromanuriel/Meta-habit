package com.example.meta_habit.data.repository

import androidx.room.util.appendPlaceholders
import com.example.meta_habit.data.db.AppDatabase
import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.data.db.entity.HabitTaskEntity

class HabitRepository(
    private val appDatabase: AppDatabase
) {

    suspend fun onSaveHabit(
        title: String,
        repetition: Int,
        hasReminder: Boolean,
        dateReminder: Long,
        tag: Int,
        color: Int

    ): Result<Long> {
        return try {
            val habitEntity = HabitEntity(
                title = title,
                repetition = repetition,
                hasReminder = false,
                dateReminder = dateReminder,
                tag = tag,
                color = color,
                dateCreate = 1,
                dateUpdate = 1
            )
            val idHabit = appDatabase.habitDao().insertGetId(habitEntity)
            Result.success(idHabit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun onSaveHabitTask(idHabit: Long, listTask: List<String>): Result<Unit>{
        return try {
            listTask.forEach { descriptionTask ->
                val habitTaskEntity = HabitTaskEntity(
                    habitId = idHabit,
                    description = descriptionTask,
                    dateCheck = 1
                )
                appDatabase.habitTaskDao().insertGetId(habitTaskEntity)
            }
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

}