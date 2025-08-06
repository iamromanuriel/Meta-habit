package com.example.meta_habit.data.task.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.meta_habit.data.task.workManager.NotificationHabitReminder

class AlarmReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context, p1: Intent?) {
        val workRequest = OneTimeWorkRequestBuilder<NotificationHabitReminder>()
            .addTag("daily_habit_worker")
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}