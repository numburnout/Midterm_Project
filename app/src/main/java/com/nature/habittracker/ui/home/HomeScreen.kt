package com.nature.habittracker.ui.home

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nature.habittracker.data.SeasonalTheme
import com.nature.habittracker.ui.BotanicalBackground
import com.nature.habittracker.ui.missions

@Composable
fun HomeScreen(
    theme: SeasonalTheme,
    streak: Int,
    onNavigateToGarden: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onStreakIncrement: () -> Unit
) {
    val todaysMission = remember { missions.random() }
    var missionCompleted by remember { mutableStateOf(false) }
    var showSuccessAnim by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "sprout")
    val sproutRotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sprout_rotate"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.background)
    ) {
        // Botanical background art
        BotanicalBackground(theme.primary, theme.secondary, theme.accent)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 48.dp)
        ) {
            // ── Header ──
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(
                        imageVector = Icons.Default.Spa,
                        contentDescription = "Sprout",
                        tint = theme.primary,
                        modifier = Modifier
                            .size(32.dp)
                    )
                    Column {
                        Text(
                            text = "Your Growth Streak",
                            color = Color(0xFF7A7A75),
                            fontSize = 13.sp
                        )
                        Text(
                            text = "$streak Days Growing",
                            color = theme.primary,
                            fontSize = 22.sp,
                            fontFamily = FontFamily.Serif
                        )
                    }
                }
                IconButton(
                    onClick = onNavigateToSettings,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.7f))
                ) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color(0xFF7A7A75))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Mission Card ──
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(28.dp)) {
                    Text(
                        text = "TODAY'S MISSION",
                        color = Color(0xFF7A7A75),
                        fontSize = 11.sp,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = todaysMission,
                        color = Color(0xFF3D3D3A),
                        fontSize = 19.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    AnimatedContent(
                        targetState = missionCompleted,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "mission_button"
                    ) { completed ->
                        if (!completed) {
                            Button(
                                onClick = {
                                    showSuccessAnim = true
                                    missionCompleted = true
                                    onStreakIncrement()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp),
                                shape = RoundedCornerShape(24.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = theme.primary
                                )
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Success! I did it", fontSize = 16.sp)
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp)
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(theme.primary.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(Icons.Default.Spa, contentDescription = null, tint = theme.primary, modifier = Modifier.size(20.dp))
                                    Text("Mission Completed! 🌱", color = theme.primary, fontSize = 16.sp)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Daily Garden Button ──
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onNavigateToGarden),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.85f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF0F4EF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Spa,
                                contentDescription = null,
                                tint = Color(0xFF8B9F87),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Column {
                            Text("My Daily Garden", color = Color(0xFF7A7A75), fontSize = 13.sp)
                            Text("View your routine tasks", color = Color(0xFF3D3D3A), fontSize = 15.sp)
                        }
                    }
                    Text("→", color = Color(0xFF8B9F87), fontSize = 20.sp)
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
