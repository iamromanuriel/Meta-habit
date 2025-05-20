package com.example.meta_habit.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.meta_habit.data.db.entity.HabitEntity

@Dao
interface DaoHabit{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habitEntity: HabitEntity): Long
}