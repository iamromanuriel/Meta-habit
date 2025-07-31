package com.example.meta_habit.data.task

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object WorkScheduler {

    fun saveTaskLogger(context: Context){
        val now = Calendar.getInstance()
        val scheduledTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 0)
            if(before(now)){
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val delay = scheduledTime.timeInMillis - now.timeInMillis
        val delayInMinutes = TimeUnit.MILLISECONDS.toMinutes(delay)

        val workRequest = PeriodicWorkRequestBuilder<DailyValidationTaskWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(delayInMinutes, TimeUnit.MINUTES)
            .build()


        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_validation_init",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

    }

    fun createNotification(context: Context){
        val now = Calendar.getInstance()
        val scheduledTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if(before(now)){
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val delayInMillis = scheduledTime.timeInMillis - now.timeInMillis
        val delayInMinutes = TimeUnit.MILLISECONDS.toMinutes(delayInMillis)

        val workRequest = PeriodicWorkRequestBuilder<NotificationHabitReminder>(24, TimeUnit.HOURS)
            .setInitialDelay(delayInMinutes, TimeUnit.MINUTES)
            .addTag("Notification_scheduler")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "notification_habit",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

    }
}