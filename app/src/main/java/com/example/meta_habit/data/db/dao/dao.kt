package com.example.meta_habit.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.data.db.entity.HabitTaskEntity
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.data.db.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoHabit{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGetId(habitEntity: HabitEntity): Long

    @Query("SELECT * FROM habit")
    fun getListOfHabit(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habit where repetition > 0")
    fun getAllHabitRepeat(): List<HabitEntity>

    @Transaction
    @Query("SELECT * FROM habit")
    fun getListOfHabitWithTasks(): Flow<List<HabitWithTasks>>

    @Transaction
    @Query("SELECT * FROM habit WHERE id = :idHabit")
    fun getHabitWithTask(idHabit: Long): Flow<HabitWithTasks?>

    @Delete
    suspend fun deleteHabit(habitEntity: HabitEntity)

    @Update
    suspend fun updateHabit(habitEntity: HabitEntity): Int
}

@Dao
interface DaoHabitTask{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGetId(habitTaskEntity: HabitTaskEntity): Long

    @Update
    suspend fun updateHabitTask(habitTaskEntity: HabitTaskEntity): Int
}

@Dao
interface DaoNotification{
    @Query("SELECT * FROM notifications")
    fun getListNotification(): Flow<List<NotificationEntity>>
}