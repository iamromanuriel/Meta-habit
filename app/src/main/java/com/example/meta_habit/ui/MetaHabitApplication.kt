package com.example.meta_habit.ui

import android.app.Application
import com.example.meta_habit.di.initKoin

class MetaHabitApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}