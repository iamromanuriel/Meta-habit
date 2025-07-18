package com.example.meta_habit.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "habit")
data class HabitEntity(
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
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index("habitId")]
)
data class HabitTaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var habitId: Long,
    var description: String,
    var isCheck: Boolean = false,
    var dateCheck: Long? = null,
    var dateCreate: Long
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
    var seen: Boolean = false,
    var dateCreate: Long
)

data class HabitWithTasks(
    @Embedded val habit: HabitEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "habitId",
    )
    val task: List<HabitTaskEntity>
)

@Entity(
    tableName = "habit_log",
    foreignKeys = [
        ForeignKey(
            entity = HabitTaskEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitTaskId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("habitTaskId")]
)
data class HabitLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val habitTaskId: Long,
    val date: Long ? = null,
    val isCompleted: Boolean = false,
    var dateCreate: Long
)

data class HabitTaskWithLogs(
    @Embedded val task: HabitTaskEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "habitTaskId"
    )

    val logs : List<HabitLogEntity>
)

data class HabitWithTaskAndLog(
    @Embedded val habit: HabitEntity,
    @Relation(
        entity = HabitTaskEntity::class,
        parentColumn = "id",
        entityColumn = "habitId"
    )
    val task: List<HabitTaskWithLogs>
)
