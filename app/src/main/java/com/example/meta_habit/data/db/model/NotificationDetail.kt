package com.example.meta_habit.data.db.model

data class NotificationDetail(
    val id: Long,
    val habitId: Long,
    val type: Int,
    val scheduledAt: Long,
    val isActive: Boolean,
    val seen: Boolean,
    val title: String,
    val description: String
)