package com.example.meta_habit.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "habit")
data class HabitEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String? = null,
    var repetition: Int? = null,
    var hasReminder: Boolean? = false,
    var dateReminder: Long? = null,
    var description: String? = null,
    var isPinned: Boolean? = false,
    var isCompleted: Boolean? = false,
    var tag: Int? = null,
    var color: Int? = null,
    var dateCreate: Long,
    var dateUpdate: Long
)

@Entity(
    tableName = "habit_task",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
    )],
    indices = [Index("habitId")]
    )
data class HabitTaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var habitId: Long,
    var description: String,
    var isCheck: Boolean = false,
    val dateCheck: Long
)

@Entity(
    tableName = "notifications",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitId"]
        )],
    indices = [Index("habitId")]
    )
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var habitId: Long,
    var type: Int? = null,
    var scheduledAt: Long? = null,
    var isActive: Boolean = false,
    var seen: Boolean = false
)

data class HabitWithTasks(
    val habit: HabitEntity,
    val task: List<HabitTaskEntity>
)

