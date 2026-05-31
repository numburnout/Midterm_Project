package com.nature.habittracker.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EnergySavingsLeaf
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import com.nature.habittracker.data.TaskIcon

@Composable
fun TaskIconImage(icon: TaskIcon, modifier: Modifier = Modifier, tint: Color = Color.Unspecified) {
    val vector: ImageVector = when (icon) {
        TaskIcon.LEAF -> Icons.Default.EnergySavingsLeaf
        TaskIcon.SUN -> Icons.Default.WbSunny
        TaskIcon.DROPLET -> Icons.Default.Opacity
    }
    Icon(imageVector = vector, contentDescription = icon.name, modifier = modifier, tint = tint)
}

@Composable
fun BotanicalBackground(primaryColor: Color, secondaryColor: Color, accentColor: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawBotanicalCornerTopLeft(primaryColor, secondaryColor)
        drawBotanicalCornerBottomRight(accentColor, primaryColor)
    }
}

private fun DrawScope.drawBotanicalCornerTopLeft(primary: Color, secondary: Color) {
    val r = size.width * 0.15f
    drawCircle(
        color = primary.copy(alpha = 0.1f),
        radius = r,
        center = Offset(r * 0.9f, r * 0.9f)
    )
    drawCircle(
        color = secondary.copy(alpha = 0.07f),
        radius = r * 0.65f,
        center = Offset(r * 1.6f, r * 1.6f)
    )
}

private fun DrawScope.drawBotanicalCornerBottomRight(accent: Color, primary: Color) {
    val r = size.width * 0.18f
    drawCircle(
        color = accent.copy(alpha = 0.1f),
        radius = r,
        center = Offset(size.width - r * 0.9f, size.height - r * 0.9f)
    )
    drawCircle(
        color = primary.copy(alpha = 0.07f),
        radius = r * 0.65f,
        center = Offset(size.width - r * 1.6f, size.height - r * 1.6f)
    )
}

val missions = listOf(
    "Take a 10-minute walk outside and notice three beautiful things.",
    "Drink a warm cup of tea and take three deep breaths.",
    "Write down one thing you're grateful for today.",
    "Stretch for 5 minutes while thinking of something positive.",
    "Send a kind message to someone you care about.",
    "Spend 15 minutes doing something creative just for fun.",
    "Take a break from screens for 30 minutes.",
    "Listen to your favorite song and dance like nobody's watching."
)
