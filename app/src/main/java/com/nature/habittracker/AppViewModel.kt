package com.nature.habittracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nature.habittracker.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val taskRepo = TaskRepository(db.taskDao())
    private val prefsRepo = UserPreferencesRepository(application)

    val tasks: StateFlow<List<Task>> = taskRepo.allTasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val season: StateFlow<Season> = prefsRepo.season
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Season.SPRING)

    val language: StateFlow<Language> = prefsRepo.language
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Language.EN)

    val streak: StateFlow<Int> = prefsRepo.streak
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 7)

    init {
        // Seed default tasks if empty
        viewModelScope.launch {
            taskRepo.allTasks.first().let { existing ->
                if (existing.isEmpty()) {
                    TaskRepository.defaultTasks.forEach { taskRepo.insert(it) }
                }
            }
        }
    }

    fun addTask(text: String, icon: TaskIcon) {
        viewModelScope.launch {
            taskRepo.insert(Task(id = UUID.randomUUID().toString(), text = text, icon = icon))
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch { taskRepo.update(task) }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch { taskRepo.delete(task) }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            taskRepo.toggleCompletion(task.id, !task.completed)
        }
    }

    fun setSeason(season: Season) {
        viewModelScope.launch { prefsRepo.setSeason(season) }
    }

    fun setLanguage(language: Language) {
        viewModelScope.launch { prefsRepo.setLanguage(language) }
    }

    fun incrementStreak() {
        viewModelScope.launch { prefsRepo.setStreak(streak.value + 1) }
    }
}
