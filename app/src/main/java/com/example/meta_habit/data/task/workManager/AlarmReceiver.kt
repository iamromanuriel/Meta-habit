package com.example.meta_habit.data.task.workManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.meta_habit.utils.NotificationHabit

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, p1: Intent?) {
        val workRequest = OneTimeWorkRequestBuilder<NotificationHabitReminder>().build()
        WorkManager.getInstance(context.applicationContext).enqueue(workRequest)
    }
}