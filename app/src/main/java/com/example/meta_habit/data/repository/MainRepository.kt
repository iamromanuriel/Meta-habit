package com.example.meta_habit.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.meta_habit.data.db.AppDatabase
import com.example.meta_habit.data.db.entity.HabitLogEntity
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.ui.utils.RepeatType
import com.example.meta_habit.ui.utils.getRepeatType
import kotlinx.coroutines.flow.map

class MainRepository(
    private val appDatabase: AppDatabase
) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun onReCreateTaskHabitRepeat() {
        appDatabase.habitDao().getListOfHabitWithTasks()
            .map { habits ->
                habits.filter { getRepeatType((it.habit.repetition ?: 0)) != RepeatType.ONLY_ONE }.forEach { habitEntity ->
                    when(getRepeatType(habitEntity.habit.repetition?:0)){
                        RepeatType.DAILY -> {

                        }
                        RepeatType.WEEKLY -> {

                        }
                        RepeatType.MONTHLY -> {
                        }
                        else -> {

                        }
                    }
                }
            }

    }


    private suspend fun restoreHabitTask(habitWithTasks: HabitWithTasks): Result<Unit>{
        return try {
            habitWithTasks.task.forEach { habitTask ->

                val habitTaskLog = HabitLogEntity(
                    habitTaskId = habitTask.id,
                    date = habitTask.dateCheck,
                    isCompleted = habitTask.isCheck
                )
                appDatabase.habitTaskLogger().insertHabitLog(habitTaskLog)

                habitTask.isCheck = false
                habitTask.dateCheck = null
                appDatabase.habitTaskDao().updateHabitTask(habitTask)

            }
            Result.success(Unit)
        }catch (e: Exception){
                Result.failure(e)
        }
    }

}
