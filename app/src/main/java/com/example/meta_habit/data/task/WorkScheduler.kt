package com.example.meta_habit.data.task

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object WorkScheduler {

    fun saveTaskLogger(context: Context){
        val timeZone = TimeZone.getDefault()
        val now = Calendar.getInstance(timeZone)

        val calendar = Calendar.getInstance(timeZone).apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if(before(now)){
                add(Calendar.DATE, 1)
            }
        }

        val initialDelay = calendar.timeInMillis - now.timeInMillis

        val workRequest = PeriodicWorkRequestBuilder<DailyValidationTaskWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()


        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_validation_init",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

    }

    fun createNotification(context: Context){
        val timeZone = TimeZone.getDefault()
        val now = Calendar.getInstance(timeZone)

        val calendar = Calendar.getInstance(timeZone).apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 12)
            set(Calendar.SECOND, 0)

            if(before(now)){
                add(Calendar.DATE, 1)
            }
        }

        /**
         * Validate time launch notification (TIME-ZONE)
         */

        val initialDelay = calendar.timeInMillis - now.timeInMillis

        val workRequest = PeriodicWorkRequestBuilder<NotificationHabitReminder>(1, TimeUnit.DAYS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .addTag("Notification_scheduler")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "notification_habit",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

    }
}