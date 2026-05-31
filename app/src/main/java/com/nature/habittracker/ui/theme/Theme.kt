package com.nature.habittracker.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nature.habittracker.data.Season
import com.nature.habittracker.data.SeasonalTheme
import com.nature.habittracker.data.SeasonalThemes

val LocalSeasonTheme = staticCompositionLocalOf {
    SeasonalThemes.getTheme(Season.SPRING)
}

@Composable
fun NatureHabitTrackerTheme(
    season: Season = Season.SPRING,
    content: @Composable () -> Unit
) {
    val seasonTheme = SeasonalThemes.getTheme(season)

    CompositionLocalProvider(
        LocalSeasonTheme provides seasonTheme
    ) {
        MaterialTheme(
            typography = Typography(
                bodyLarge = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                titleLarge = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 24.sp
                )
            ),
            shapes = Shapes(
                small = RoundedCornerShape(12.dp),
                medium = RoundedCornerShape(20.dp),
                large = RoundedCornerShape(28.dp),
                extraLarge = RoundedCornerShape(32.dp)
            ),
            content = content
        )
    }
}
