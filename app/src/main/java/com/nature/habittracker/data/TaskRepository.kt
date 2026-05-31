package com.nature.habittracker.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insert(task: Task) = taskDao.insertTask(task)
    suspend fun update(task: Task) = taskDao.updateTask(task)
    suspend fun delete(task: Task) = taskDao.deleteTask(task)
    suspend fun toggleCompletion(id: String, completed: Boolean) =
        taskDao.updateTaskCompletion(id, completed)

    companion object {
        val defaultTasks = listOf(
            Task(id = "1", text = "Morning meditation (5 min)", icon = TaskIcon.SUN),
            Task(id = "2", text = "Drink a glass of water", icon = TaskIcon.DROPLET),
            Task(id = "3", text = "Review today's schedule", icon = TaskIcon.LEAF),
            Task(id = "4", text = "Healthy breakfast", icon = TaskIcon.SUN),
            Task(id = "5", text = "30 minutes of study", icon = TaskIcon.LEAF),
            Task(id = "6", text = "Take a mindful break", icon = TaskIcon.DROPLET),
            Task(id = "7", text = "Evening gratitude journal", icon = TaskIcon.SUN),
            Task(id = "8", text = "Wind down (no screens)", icon = TaskIcon.LEAF)
        )
    }
}
