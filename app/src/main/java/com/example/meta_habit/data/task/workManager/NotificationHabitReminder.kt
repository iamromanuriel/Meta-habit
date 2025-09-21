package com.example.meta_habit.data.task.workManager

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.meta_habit.data.db.AppDatabase
import com.example.meta_habit.data.db.entity.NotificationEntity
import com.example.meta_habit.utils.NotificationChannel
import com.example.meta_habit.utils.NotificationHabit
import com.example.meta_habit.utils.NotificationTypes
import com.example.meta_habit.utils.RepeatType
import com.example.meta_habit.utils.getNextAWeek
import com.example.meta_habit.utils.getNextThreeDayReminderDate
import com.example.meta_habit.utils.getRepeatType
import com.example.meta_habit.utils.nextDayMonth
import com.example.meta_habit.utils.toLocalDate
import java.time.LocalDate
import java.util.Date

class NotificationHabitReminder(
    appContext: Context,
    params: WorkerParameters,
    private val appDatabase: AppDatabase
) :
    CoroutineWorker(appContext, params) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return super.getForegroundInfo(

        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        return try {
            validaReminderHabitToday()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun validaReminderHabitToday() {
        val habits = appDatabase.habitDao().getAllHabitRepeat()
        Log.d("CreateNotification","initValidation ${habits}")
        habits.forEach { habit ->
            val now = LocalDate.now()
            Log.d("CreateNotification","getHabits ${habit}")

            when (getRepeatType(habit.repetition ?: 0)) {
                RepeatType.ONLY_ONE -> {
                    if((habit.dateReminder)?.toLocalDate() == now){
                        saveInfNotification(habit.id)
                        NotificationHabit.showNotification(
                            applicationContext,
                            NotificationChannel.MAIN,
                            habit.title ?: "Recordatorio diario",
                            habit.description?: ""
                        )
                    }
                }

                RepeatType.DAILY -> {
                    Log.d("CreateNotification","Daily")
                    saveInfNotification(habit.id)
                    NotificationHabit.showNotification(
                        applicationContext,
                        NotificationChannel.MAIN,
                        habit.title ?: "Recordatorio diario",
                        habit.description?: ""
                    )
                }

                RepeatType.WEEKLY -> {
                    if(getNextAWeek(habit.dateReminder!!.toLocalDate()) == now){
                        saveInfNotification(habit.id)
                        NotificationHabit.showNotification(
                            applicationContext,
                            NotificationChannel.MAIN,
                            habit.title ?: "Recordatorio semanal",
                            habit.description?: ""
                        )
                    }
                }
                RepeatType.MONTHLY -> {
                    if(nextDayMonth(habit.dateReminder!!.toLocalDate()) == now){
                        saveInfNotification(habit.id)
                        NotificationHabit.showNotification(
                            applicationContext,
                            NotificationChannel.MAIN,
                            habit.title ?: "Recordatorio mensual",
                            habit.description?: ""
                        )
                    }
                }
                RepeatType.THREE_DAYS -> {
                    if (getNextThreeDayReminderDate(habit.dateReminder!!.toLocalDate()) == now) {
                        saveInfNotification(habit.id)
                    }
                }

                null -> {}
            }
        }
    }


    private suspend fun saveInfNotification(habitId: Long): kotlin.Result<Unit> {
        return try {
            val notification = NotificationEntity(
                habitId = habitId,
                type = NotificationTypes.Remember.ordinal,
                scheduledAt = Date().time,
                isActive = true,
                dateCreate = System.currentTimeMillis()
            )

            appDatabase.habitNotification().insertGetId(notificationEntity = notification)
            kotlin.Result.success(Unit)
        } catch (e: Exception) {
            kotlin.Result.failure(e)
        }
    }
}