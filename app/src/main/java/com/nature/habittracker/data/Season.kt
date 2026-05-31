package com.nature.habittracker.data

import androidx.compose.ui.graphics.Color

enum class Season {
    SPRING, SUMMER, FALL, WINTER
}

enum class Language {
    EN, ES, FR
}

data class SeasonalTheme(
    val primary: Color,
    val secondary: Color,
    val accent: Color,
    val background: Color,
    val name: String,
    val emoji: String
)

object SeasonalThemes {
    val themes = mapOf(
        Season.SPRING to SeasonalTheme(
            primary = Color(0xFF8B9F87),
            secondary = Color(0xFFF4A5AE),
            accent = Color(0xFFC9B8A8),
            background = Color(0xFFF5F3EF),
            name = "Spring Bloom",
            emoji = "🌸"
        ),
        Season.SUMMER to SeasonalTheme(
            primary = Color(0xFF7EB09B),
            secondary = Color(0xFFF9D77E),
            accent = Color(0xFFA8C9D4),
            background = Color(0xFFFEF9F3),
            name = "Summer Sun",
            emoji = "☀️"
        ),
        Season.FALL to SeasonalTheme(
            primary = Color(0xFFC17F5B),
            secondary = Color(0xFFD4A59A),
            accent = Color(0xFF8B7355),
            background = Color(0xFFF7F0E8),
            name = "Autumn Harvest",
            emoji = "🍂"
        ),
        Season.WINTER to SeasonalTheme(
            primary = Color(0xFF7A9AA8),
            secondary = Color(0xFFB8A8C9),
            accent = Color(0xFFA8B8B8),
            background = Color(0xFFF2F4F6),
            name = "Winter Frost",
            emoji = "❄️"
        )
    )

    fun getTheme(season: Season): SeasonalTheme = themes[season]!!
}
