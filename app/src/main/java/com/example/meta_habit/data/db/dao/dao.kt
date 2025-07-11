package com.example.meta_habit.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.data.db.entity.HabitLogEntity
import com.example.meta_habit.data.db.entity.HabitTaskEntity
import com.example.meta_habit.data.db.entity.HabitWithTaskAndLog
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.data.db.entity.NotificationEntity
import com.example.meta_habit.data.db.model.NotificationDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoHabit {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGetId(habitEntity: HabitEntity): Long

    @Query("SELECT * FROM habit")
    fun getListOfHabit(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habit where repetition > 0")
    fun getAllHabitRepeat(): List<HabitEntity>

    @Transaction
    @Query("SELECT * FROM habit")
    fun getListOfHabitWithTasksFlow(): Flow<List<HabitWithTasks>>

    @Transaction
    @Query("SELECT * FROM habit")
    fun getListOfHabitWithTasks(): List<HabitWithTasks>

    @Transaction
    @Query("SELECT * FROM habit WHERE id = :idHabit")
    fun getHabitWithTask(idHabit: Long): Flow<HabitWithTasks?>

    @Transaction
    @Query("SELECT * FROM habit WHERE id = :idHabit")
    fun getHabitWithTaskAndLog(idHabit: Long): Flow<HabitWithTaskAndLog?>

    @Delete
    suspend fun deleteHabit(habitEntity: HabitEntity)

    @Update
    suspend fun updateHabit(habitEntity: HabitEntity): Int

    @Query("SELECT * FROM habit WHERE id = :idHabit")
    fun getHabitById(idHabit: Long): HabitEntity?
}

@Dao
interface DaoHabitTask {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGetId(habitTaskEntity: HabitTaskEntity): Long

    @Update
    suspend fun updateHabitTask(habitTaskEntity: HabitTaskEntity): Int
}

@Dao
interface DaoNotification {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGetId(notificationEntity: NotificationEntity): Long

    @Query("SELECT * FROM notifications")
    fun getListNotification(): Flow<List<NotificationEntity>>

    @Query("SELECT n.id as id, n.habitId as habitId, n.type as type, n.scheduledAt as scheduledAt, n.isActive as isActive, n.seen as seen, h.title as title, h.description as description FROM notifications as n JOIN habit h ON n.habitId = h.id")
    fun getListNotificationDetail(): Flow<List<NotificationDetail>>

    @Query("UPDATE notifications SET seen = 1 WHERE id = :idNotification")
    fun makeNotificationSeen(idNotification: Long)
}

@Dao
interface DaoHabitTaskLogger {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHabitLog(habitLogEntity: HabitLogEntity)
}