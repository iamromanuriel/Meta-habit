package com.example.meta_habit.ui.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.meta_habit.MainActivity
import com.example.meta_habit.R
import com.example.meta_habit.ui.utils.NotificationChannel as ChannelHabit


object NotificationHabit{

    @SuppressLint("WrongConstant")
    fun createNotificationChannel(
        context: Context,
        name: String,
        descriptionText: String,
        importance: Int = NotificationManagerCompat.IMPORTANCE_DEFAULT
    ){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(com.example.meta_habit.ui.utils.NotificationChannel.MAIN.value, name, importance).apply{
                description = descriptionText
            }
            val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(context)
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun showNotification(
        context: Context,
        channel: ChannelHabit,
        title: String,
        content: String,
        @DrawableRes draw: Int = R.drawable.group_24,
        priority: Int = NotificationManagerCompat.IMPORTANCE_DEFAULT
    ) {

        val builder = NotificationCompat.Builder(context, channel.value)
            .setSmallIcon(draw)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(priority)
            .setContentIntent(intentApp(context))
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }
    }


    private fun intentApp(context: Context): PendingIntent{
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return pendingIntent
    }
}