package com.example.meta_habit.data.task

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyRepository{
    fun getMessage(): String = "!<Hola Mundo>"
}

class DailyValidationTaskWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return Result.success()
    }


}