package com.example.meta_habit.ui

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.meta_habit.data.task.DailyValidationTaskWorker
import com.example.meta_habit.data.task.WorkScheduler
import com.example.meta_habit.di.initKoin
import org.koin.java.KoinJavaComponent.get
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MetaHabitApplication: Application(), Configuration.Provider {

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(get(KoinWorkerFactory::class.java))
            .build()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        initKoin(context = this)
        WorkScheduler.createNotification(this)
        WorkScheduler.saveTaskLogger(this)

    }


}