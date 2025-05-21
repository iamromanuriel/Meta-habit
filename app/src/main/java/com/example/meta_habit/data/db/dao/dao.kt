package com.example.meta_habit.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.data.db.entity.HabitTaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoHabit{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGetId(habitEntity: HabitEntity): Long

    @Query("SELECT * FROM habit")
    fun getListOfHabit(): Flow<List<HabitEntity>>

    @Delete
    suspend fun deleteHabit(habitEntity: HabitEntity): Int

    @Update
    suspend fun updateHabit(habitEntity: HabitEntity): Int
}

@Dao
interface DaoHabitTask{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGetId(habitTaskEntity: HabitTaskEntity): Long
}