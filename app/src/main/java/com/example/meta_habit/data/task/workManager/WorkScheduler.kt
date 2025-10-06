package com.example.meta_habit.data.task.workManager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.meta_habit.data.task.alarmManager.AlarmReceiver
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object WorkScheduler {

    @SuppressLint("ScheduleExactAlarm")
    fun onCreateNotification(
        context: Context
    ){
        val intent = Intent(context, com.example.meta_habit.data.task.workManager.AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        val targetTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }

        }

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            targetTime.timeInMillis,
            pendingIntent
        )
    }

    fun saveTaskLogger(context: Context) {

        val timeZone = TimeZone.getDefault()
        val now = Calendar.getInstance(timeZone)

        val scheduledTime = Calendar.getInstance(timeZone).apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 0)
            if (before(now)) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val delay = scheduledTime.timeInMillis - System.currentTimeMillis()

        val workRequest = PeriodicWorkRequestBuilder<DailyValidationTaskWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()


        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_validation_init",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

    }

    @SuppressLint("ScheduleExactAlarm")
    fun createNotification(context: Context) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val targetTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }

        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            targetTime.timeInMillis,
            pendingIntent
        )

    }
}