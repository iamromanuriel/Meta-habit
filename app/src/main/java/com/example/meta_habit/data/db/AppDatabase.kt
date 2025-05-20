package com.example.meta_habit.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.meta_habit.data.db.dao.DaoHabit
import com.example.meta_habit.data.db.dao.DaoHabitTask
import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.data.db.entity.HabitTaskEntity
import com.example.meta_habit.data.db.entity.NotificationEntity

const val databaseVersion = 1
const val databaseName = "meta_habit_db"

@Database(
    entities = [
        HabitEntity::class,
        HabitTaskEntity::class,
        NotificationEntity::class],
    version = databaseVersion
    )
abstract class AppDatabase: RoomDatabase() {
    abstract fun habitDao(): DaoHabit
    abstract fun habitTaskDao(): DaoHabitTask
}


