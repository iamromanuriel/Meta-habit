package com.example.meta_habit.data.task

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.meta_habit.data.db.AppDatabase
import com.example.meta_habit.data.db.entity.NotificationEntity
import com.example.meta_habit.ui.utils.NotificationChannel
import com.example.meta_habit.ui.utils.NotificationHabit
import com.example.meta_habit.ui.utils.NotificationTypes
import com.example.meta_habit.ui.utils.RepeatType
import com.example.meta_habit.ui.utils.getNextThreeDayReminderDate
import com.example.meta_habit.ui.utils.getRepeatType
import com.example.meta_habit.ui.utils.toLocalDate
import java.time.LocalDate
import java.util.Date

class NotificationHabitReminder(
    appContext: Context,
    params: WorkerParameters,
    private val appDatabase: AppDatabase
) :
    CoroutineWorker(appContext, params) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        return try {
            //validaReminderHabitToday()
            NotificationHabit.showNotification(applicationContext, NotificationChannel.MAIN, "Prueba notificacion", "Prueba de notificacion programada")
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun validaReminderHabitToday(){
        val habits = appDatabase.habitDao().getAllHabitRepeat()
        habits.forEach { habit ->
            val now = LocalDate.now()

            when(getRepeatType(habit.repetition?:0)){
                RepeatType.ONLY_ONE -> {

                }
                RepeatType.DAILY -> TODO()
                RepeatType.WEEKLY -> TODO()
                RepeatType.MONTHLY -> TODO()
                RepeatType.THREE_DAYS -> {
                    if(getNextThreeDayReminderDate(habit.dateReminder!!.toLocalDate()) == now){
                        saveInfNotification(habit.id)
                    }
                }
                null -> {}
            }
        }
    }


    private suspend fun saveInfNotification(habitId: Long): kotlin.Result<Unit>{
        return try {
            val notification = NotificationEntity(
                habitId = habitId,
                type = NotificationTypes.Remember.ordinal,
                scheduledAt = Date().time,
                isActive = true,
            )

            appDatabase.habitNotification().insertGetId(notificationEntity = notification)
            kotlin.Result.success(Unit)
        }catch (e:Exception){
            kotlin.Result.failure(e)
        }
    }
}