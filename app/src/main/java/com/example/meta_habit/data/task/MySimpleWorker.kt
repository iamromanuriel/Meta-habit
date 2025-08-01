package com.example.meta_habit.data.task

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MySimpleWorker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams)  {
    override fun doWork(): Result {
        val shouldFail = inputData.getBoolean("shouldFail", false)
        if (shouldFail) {
            return Result.failure()
        }
        val outputData = Data.Builder()
            .putString("status", "Success!")
            .build()
        return Result.success(outputData)

    }

}