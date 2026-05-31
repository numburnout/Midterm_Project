package com.nature.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nature.habittracker.data.SeasonalThemes
import com.nature.habittracker.ui.home.HomeScreen
import com.nature.habittracker.ui.garden.DailyGardenScreen
import com.nature.habittracker.ui.settings.SettingsScreen
import com.nature.habittracker.ui.theme.NatureHabitTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: AppViewModel = viewModel()
            val season by viewModel.season.collectAsState()
            val language by viewModel.language.collectAsState()
            val tasks by viewModel.tasks.collectAsState()
            val streak by viewModel.streak.collectAsState()

            val theme = SeasonalThemes.getTheme(season)

            NatureHabitTrackerTheme(season = season) {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            theme = theme,
                            streak = streak,
                            onNavigateToGarden = { navController.navigate(Screen.Garden.route) },
                            onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                            onStreakIncrement = { viewModel.incrementStreak() }
                        )
                    }
                    composable(Screen.Garden.route) {
                        DailyGardenScreen(
                            theme = theme,
                            tasks = tasks,
                            onNavigateBack = { navController.popBackStack() },
                            onToggle = { viewModel.toggleTask(it) },
                            onAdd = { text, icon -> viewModel.addTask(text, icon) },
                            onUpdate = { viewModel.updateTask(it) },
                            onDelete = { viewModel.deleteTask(it) }
                        )
                    }
                    composable(Screen.Settings.route) {
                        SettingsScreen(
                            theme = theme,
                            currentSeason = season,
                            currentLanguage = language,
                            onSeasonChange = { viewModel.setSeason(it) },
                            onLanguageChange = { viewModel.setLanguage(it) },
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
