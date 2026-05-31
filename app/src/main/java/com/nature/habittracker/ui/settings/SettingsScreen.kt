package com.nature.habittracker.ui.settings

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nature.habittracker.data.Language
import com.nature.habittracker.data.Season
import com.nature.habittracker.data.SeasonalTheme
import com.nature.habittracker.data.SeasonalThemes
import com.nature.habittracker.ui.BotanicalBackground

data class LangOption(val code: Language, val name: String, val emoji: String)

val languages = listOf(
    LangOption(Language.EN, "English", "🇬🇧"),
    LangOption(Language.ES, "Español", "🇪🇸"),
    LangOption(Language.FR, "Français", "🇫🇷")
)

@Composable
fun SettingsScreen(
    theme: SeasonalTheme,
    currentSeason: Season,
    currentLanguage: Language,
    onSeasonChange: (Season) -> Unit,
    onLanguageChange: (Language) -> Unit,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.background)
    ) {
        BotanicalBackground(theme.primary, theme.secondary, theme.accent)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 48.dp)
        ) {
            // ── Header ──
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF7A7A75)
                    )
                }
                Column {
                    Text(
                        "Settings",
                        color = Color(0xFF3D3D3A),
                        fontSize = 26.sp,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        "Customize your experience",
                        color = Color(0xFF7A7A75),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Language Card ──
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF0F4EF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Language,
                                contentDescription = null,
                                tint = theme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text("Language", color = Color(0xFF3D3D3A), fontSize = 16.sp)
                            Text("Choose your preferred language", color = Color(0xFF7A7A75), fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    languages.forEach { lang ->
                        val isSelected = currentLanguage == lang.code
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) theme.primary else Color(0xFFFAFAF8))
                                .clickable { onLanguageChange(lang.code) }
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(lang.emoji, fontSize = 22.sp)
                                Text(
                                    lang.name,
                                    color = if (isSelected) Color.White else Color(0xFF3D3D3A),
                                    fontSize = 15.sp
                                )
                            }
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.3f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Seasonal Mode Card ──
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF0F4EF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Palette,
                                contentDescription = null,
                                tint = theme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text("Seasonal Mode", color = Color(0xFF3D3D3A), fontSize = 16.sp)
                            Text("Change the look and feel", color = Color(0xFF7A7A75), fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 2x2 Grid
                    val seasons = Season.entries.toList()
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        seasons.chunked(2).forEach { row ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                row.forEach { season ->
                                    val sTheme = SeasonalThemes.getTheme(season)
                                    val isSelected = currentSeason == season
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(
                                                Brush.linearGradient(
                                                    listOf(sTheme.primary, sTheme.secondary)
                                                )
                                            )
                                            .border(
                                                width = if (isSelected) 3.dp else 0.dp,
                                                color = if (isSelected) Color.White else Color.Transparent,
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                            .clickable { onSeasonChange(season) }
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(sTheme.emoji, fontSize = 28.sp)
                                            Spacer(Modifier.height(4.dp))
                                            Text(sTheme.name, color = Color.White, fontSize = 12.sp)
                                            if (isSelected) {
                                                Spacer(Modifier.height(6.dp))
                                                Box(
                                                    modifier = Modifier
                                                        .size(22.dp)
                                                        .clip(CircleShape)
                                                        .background(Color.White.copy(alpha = 0.3f)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        Icons.Default.Check,
                                                        contentDescription = null,
                                                        tint = Color.White,
                                                        modifier = Modifier.size(12.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                // fill empty slot if odd count
                                if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Decorative dots ──
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                listOf(theme.primary, theme.secondary, theme.accent).forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(color.copy(alpha = 0.3f))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}
