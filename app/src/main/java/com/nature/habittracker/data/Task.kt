package com.nature.habittracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TaskIcon {
    LEAF, SUN, DROPLET
}

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: String,
    val text: String,
    val completed: Boolean = false,
    val icon: TaskIcon = TaskIcon.LEAF,
    val createdAt: Long = System.currentTimeMillis()
)
