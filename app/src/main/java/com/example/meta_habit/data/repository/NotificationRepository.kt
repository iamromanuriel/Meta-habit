package com.example.meta_habit.data.repository

import com.example.meta_habit.data.db.AppDatabase
import com.example.meta_habit.data.db.entity.NotificationEntity
import com.example.meta_habit.data.db.model.NotificationDetail
import kotlinx.coroutines.flow.Flow

class NotificationRepository(
    private val appDatabase: AppDatabase
) {

    fun getListNotification(): Flow<List<NotificationEntity>> {
        return appDatabase.habitNotification().getListNotification()
   }

    fun getListNotificationDetail(): Flow<List<NotificationDetail>>{
        return appDatabase.habitNotification().getListNotificationDetail()
    }

    suspend fun makeNotificationSeen(idNotification: Long): Result<Unit>{
        return try {
            appDatabase.habitNotification().makeNotificationSeen(idNotification)
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}