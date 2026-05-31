package com.nature.habittracker

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Garden : Screen("garden")
    object Settings : Screen("settings")
}
