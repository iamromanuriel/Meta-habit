package com.example.meta_habit.data.task.workManager

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.meta_habit.data.db.AppDatabase
import com.example.meta_habit.data.db.entity.HabitLogEntity
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.utils.RepeatType
import com.example.meta_habit.utils.getNextAWeek
import com.example.meta_habit.utils.getNextThreeDayReminderDate
import com.example.meta_habit.utils.getRepeatType
import com.example.meta_habit.utils.nextDayMonth
import com.example.meta_habit.utils.toLocalDate
import java.time.LocalDate


class DailyValidationTaskWorker(appContext: Context, workerParams: WorkerParameters, private val appDatabase: AppDatabase) :
    CoroutineWorker(appContext, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        return try {
            saveHabitTaskLogger()
            Result.success()
        }catch (e: Exception){
            Result.failure()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun saveHabitTaskLogger(){
        val habits = appDatabase.habitDao().getListOfHabitWithTasks()

        habits.filter { getRepeatType((it.habit.repetition ?: 0)) != RepeatType.ONLY_ONE }.forEach { habitWithTask ->

            when(getRepeatType(habitWithTask.habit.repetition?:0)){
                RepeatType.DAILY -> {
                    restoreHabitTask(habitWithTask)
                }
                RepeatType.THREE_DAYS -> {
                    val nexDay = getNextThreeDayReminderDate((habitWithTask.habit.dateReminder?:0).toLocalDate())
                    val now = LocalDate.now()

                    if(now == nexDay){
                        restoreHabitTask(habitWithTask)
                    }
                }
                RepeatType.WEEKLY -> {
                    val nextDay = getNextAWeek((habitWithTask.habit.dateReminder?:0).toLocalDate())
                    val now = LocalDate.now()

                    if(nextDay == now){
                        restoreHabitTask(habitWithTask)
                    }
                }
                RepeatType.MONTHLY -> {
                    val nextDay = nextDayMonth((habitWithTask.habit.dateReminder?:0).toLocalDate())
                    val now = LocalDate.now()

                    if(nextDay == now){
                        restoreHabitTask(habitWithTask)
                    }
                }
                else -> {

                }
            }
        }

    }

    private suspend fun restoreHabitTask(habitWithTasks: HabitWithTasks): kotlin.Result<Unit> {
        return try {
            habitWithTasks.task.forEach { habitTask ->

                val habitTaskLog = HabitLogEntity(
                    habitTaskId = habitTask.id,
                    date = habitTask.dateCheck,
                    isCompleted = habitTask.isCheck,
                    dateCreate = System.currentTimeMillis()
                )
                appDatabase.habitTaskLogger().insertHabitLog(habitTaskLog)

                habitTask.isCheck = false
                habitTask.dateCheck = null
                appDatabase.habitTaskDao().updateHabitTask(habitTask)

            }
            kotlin.Result.success(Unit)
        }catch (e: Exception){
            kotlin.Result.failure(e)
        }
    }
}