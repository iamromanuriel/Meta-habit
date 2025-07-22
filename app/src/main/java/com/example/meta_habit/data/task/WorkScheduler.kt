package com.example.meta_habit.data.task

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

object WorkScheduler {

    fun saveTaskLogger(context: Context){
        val now = LocalDateTime.now()
        val nextMidnight = now.withHour(23).withMinute(59).withSecond(59)
        val delay = Duration.between(now, nextMidnight).toMillis()

        val workRequest = PeriodicWorkRequestBuilder<DailyValidationTaskWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()


        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_validation_init",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

    }

    fun createNotification(context: Context){
        val now = LocalDateTime.now()
        val nextMidnight = now.withHour(8).withMinute(12).withSecond(0)
        val delay = Duration.between(now, nextMidnight).toMillis()

        val workRequest = PeriodicWorkRequestBuilder<NotificationHabitReminder>(1, TimeUnit.DAYS)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .addTag("Notification_scheduler")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "notification_habit",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

    }
}