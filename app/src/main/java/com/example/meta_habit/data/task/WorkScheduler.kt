package com.example.meta_habit.data.task

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

object WorkScheduler {

    fun saveTaskLogger(context: Context){
        val now = ZonedDateTime.now()

        val desiredTime = LocalTime.of(23, 59, 0)
        var nextExecutionDateTime = now.with(desiredTime)

        if(nextExecutionDateTime.isBefore(now)){
            nextExecutionDateTime = nextExecutionDateTime.plusDays(1)
        }
        val delay = Duration.between(now, nextExecutionDateTime).toMillis()

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
        val now = ZonedDateTime.now()

        val desiredTime = LocalTime.of(10, 18, 10)
        var nextExecutionDateTime = now.with(desiredTime)

        if(nextExecutionDateTime.isBefore(now)){
            nextExecutionDateTime = nextExecutionDateTime.plusDays(1)
        }
        val delay = Duration.between(now, nextExecutionDateTime).toMillis()

        val workRequest = PeriodicWorkRequestBuilder<NotificationHabitReminder>(1, TimeUnit.DAYS)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .addTag("Notification_scheduler")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "notification_habit",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )

    }
}