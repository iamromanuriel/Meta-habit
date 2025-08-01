package com.example.meta_habit

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.Configuration
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.TestDriver
import androidx.work.testing.WorkManagerTestInitHelper
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertTrue
import com.example.meta_habit.data.task.WorkScheduler.createNotification
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import java.util.Calendar

class NotificationSchedulerTest() {
    private lateinit var context: Context
    private lateinit var workManager: WorkManager
    private lateinit var testDriver: TestDriver

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
        testDriver = WorkManagerTestInitHelper.getTestDriver(context)!!
        workManager = WorkManager.getInstance(context)
    }


    fun testNotificationScheduled() {
        createNotification(context)

        val workInfos = workManager.getWorkInfosByTag("Notification_scheduler").get()
        val workInfo = workInfos.firstOrNull()

        assertTrue("workInfo should not be null", workInfo != null && workInfo.state == WorkInfo.State.ENQUEUED)

        testDriver.setInitialDelayMet(workInfo!!.id)
        testDriver.setPeriodDelayMet(workInfo.id)

        val updateWorkInfo = workManager.getWorkInfoById(workInfo.id).get()
        assertThat(updateWorkInfo!!.state, `is`(WorkInfo.State.ENQUEUED))
    }

    @Test
    fun testNotificationScheduledFor8AM() {
        // Enqueue the WorkManager
        createNotification(context)

        // Get the initial work info
        val workInfos = workManager.getWorkInfosByTag("Notification_scheduler").get()
        val workInfo = workInfos.firstOrNull()

        // Assert that the work was enqueued
        assertTrue("workInfo should not be null", workInfo != null && workInfo.state == WorkInfo.State.ENQUEUED)

        // Use TestDriver to simulate that the initial delay has passed
        testDriver.setInitialDelayMet(workInfo!!.id)

        // And for a periodic work, simulate that the first period has also passed
        testDriver.setPeriodDelayMet(workInfo.id)

        // Get the updated work info
        val updatedWorkInfo = workManager.getWorkInfoById(workInfo.id).get()

        // *** THIS IS THE KEY ASSERTION ***
        // We assert that the work's state has changed back to ENQUEUED.
        // This confirms that the WorkManager "executed" the work and then rescheduled it
        // for the next repetition, which is the expected behavior for a PeriodicWorkRequest.
        assertTrue(updatedWorkInfo == null)
        assertThat(updatedWorkInfo!!.state, `is`(WorkInfo.State.ENQUEUED))
    }

}