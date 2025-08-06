package com.example.meta_habit

import android.content.Context
import org.junit.Before
import androidx.test.core.app.ApplicationProvider
import androidx.work.Configuration
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.impl.utils.SynchronousExecutor
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import com.example.meta_habit.data.task.workManager.MySimpleWorker
import org.junit.Test
import org.junit.Assert.assertThat
import org.hamcrest.CoreMatchers.`is`


class MySimpleWorkerTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    fun testMySimpleWorkerSuccess() {

        val inputData = Data.Builder()
            .putBoolean("shouldFail", false)
            .build()

        val worker = TestListenableWorkerBuilder<MySimpleWorker>(
            context = context,
            inputData = inputData
        ).build()

        val result = worker.doWork()

        //assertThat(result, `is`(ListenableWorker.Result.success()))

        val outputData = (result as ListenableWorker.Result.Success).outputData
        assertThat(outputData.getString("status"), `is`("Success!"))

    }
}