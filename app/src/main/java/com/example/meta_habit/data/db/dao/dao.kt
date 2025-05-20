package com.example.meta_habit.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.data.db.entity.HabitTaskEntity

@Dao
interface DaoHabit{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGetId(habitEntity: HabitEntity): Long
}

@Dao
interface DaoHabitTask{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGetId(habitTaskEntity: HabitTaskEntity): Long
}