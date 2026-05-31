package com.nature.habittracker.ui.garden

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nature.habittracker.data.SeasonalTheme
import com.nature.habittracker.data.Task
import com.nature.habittracker.data.TaskIcon
import com.nature.habittracker.ui.BotanicalBackground
import com.nature.habittracker.ui.TaskIconImage

@Composable
fun DailyGardenScreen(
    theme: SeasonalTheme,
    tasks: List<Task>,
    onNavigateBack: () -> Unit,
    onToggle: (Task) -> Unit,
    onAdd: (String, TaskIcon) -> Unit,
    onUpdate: (Task) -> Unit,
    onDelete: (Task) -> Unit
) {
    val completedCount = tasks.count { it.completed }
    val totalCount = tasks.size
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f

    var showAddTask by remember { mutableStateOf(false) }
    var newTaskText by remember { mutableStateOf("") }
    var newTaskIcon by remember { mutableStateOf(TaskIcon.LEAF) }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(500, easing = EaseOut),
        label = "progress"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.background)
    ) {
        BotanicalBackground(theme.primary, theme.secondary, theme.accent)

        Column(modifier = Modifier.fillMaxSize()) {
            // ── Header ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 24.dp, top = 48.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF7A7A75))
                }
                Column {
                    Text(
                        "My Daily Garden",
                        color = Color(0xFF3D3D3A),
                        fontSize = 26.sp,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        "$completedCount of $totalCount tasks blooming",
                        color = Color(0xFF7A7A75),
                        fontSize = 13.sp
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                // ── Progress Card ──
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.7f)),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Today's Progress", color = Color(0xFF7A7A75), fontSize = 13.sp)
                                Text(
                                    "${(progress * 100).toInt()}%",
                                    color = theme.primary,
                                    fontSize = 13.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            LinearProgressIndicator(
                                progress = { animatedProgress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                color = theme.primary,
                                trackColor = Color(0xFFE8E5DF)
                            )
                        }
                    }
                }

                // ── Add Task Button ──
                item {
                    Button(
                        onClick = { showAddTask = !showAddTask },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primary)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Add New Task", fontSize = 15.sp)
                    }
                }

                // ── Add Task Form ──
                if (showAddTask) {
                    item {
                        AddTaskForm(
                            theme = theme,
                            newTaskText = newTaskText,
                            onTextChange = { newTaskText = it },
                            selectedIcon = newTaskIcon,
                            onIconSelect = { newTaskIcon = it },
                            onAdd = {
                                if (newTaskText.isNotBlank()) {
                                    onAdd(newTaskText.trim(), newTaskIcon)
                                    newTaskText = ""
                                    showAddTask = false
                                }
                            },
                            onDismiss = {
                                showAddTask = false
                                newTaskText = ""
                            }
                        )
                    }
                }

                // ── Task List ──
                itemsIndexed(tasks) { _, task ->
                    TaskItem(
                        task = task,
                        theme = theme,
                        onToggle = { onToggle(task) },
                        onUpdate = onUpdate,
                        onDelete = { onDelete(task) }
                    )
                }

                // ── Empty State ──
                if (tasks.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No tasks yet. Add your first task to start growing your garden! 🌱",
                                color = Color(0xFF7A7A75),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }

                // ── Completion Banner ──
                if (completedCount == totalCount && totalCount > 0) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = theme.primary.copy(alpha = 0.1f)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(theme.emoji, fontSize = 40.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Garden Complete!",
                                    color = theme.primary,
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily.Serif
                                )
                                Text(
                                    "You've tended to all your daily tasks. Well done!",
                                    color = Color(0xFF7A7A75),
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }

                // ── Dots ──
                item {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        listOf(theme.primary, theme.secondary, theme.accent).forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(color.copy(alpha = 0.3f))
                            )
                            Spacer(Modifier.width(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AddTaskForm(
    theme: SeasonalTheme,
    newTaskText: String,
    onTextChange: (String) -> Unit,
    selectedIcon: TaskIcon,
    onIconSelect: (TaskIcon) -> Unit,
    onAdd: () -> Unit,
    onDismiss: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("New Task", color = Color(0xFF3D3D3A), fontSize = 17.sp)
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color(0xFF7A7A75))
                }
            }
            OutlinedTextField(
                value = newTaskText,
                onValueChange = onTextChange,
                placeholder = { Text("Enter task description...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = theme.primary,
                    unfocusedBorderColor = Color(0xFFE0DDD8)
                )
            )
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Icon:", color = Color(0xFF7A7A75), fontSize = 13.sp)
                TaskIcon.entries.forEach { icon ->
                    val isSelected = selectedIcon == icon
                    IconButton(
                        onClick = { onIconSelect(icon) },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) theme.primary else Color(0xFFFAFAF8))
                    ) {
                        TaskIconImage(
                            icon = icon,
                            modifier = Modifier.size(18.dp),
                            tint = if (isSelected) Color.White else theme.primary
                        )
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = onAdd,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = theme.primary)
            ) {
                Text("Add Task")
            }
        }
    }
}

@Composable
private fun TaskItem(
    task: Task,
    theme: SeasonalTheme,
    onToggle: () -> Unit,
    onUpdate: (Task) -> Unit,
    onDelete: () -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editText by remember { mutableStateOf(task.text) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        if (isEditing) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = editText,
                    onValueChange = { editText = it },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = theme.primary,
                        unfocusedBorderColor = Color(0xFFE0DDD8)
                    )
                )
                TextButton(
                    onClick = {
                        if (editText.isNotBlank()) onUpdate(task.copy(text = editText.trim()))
                        isEditing = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = theme.primary)
                ) { Text("Save") }
                TextButton(
                    onClick = { isEditing = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF7A7A75))
                ) { Text("Cancel") }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Circular checkbox
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (task.completed) theme.primary else Color.White)
                        .border(2.dp, if (task.completed) theme.primary else theme.accent, CircleShape)
                        .clickable(onClick = onToggle),
                    contentAlignment = Alignment.Center
                ) {
                    if (task.completed) {
                        TaskIconImage(task.icon, modifier = Modifier.size(16.dp), tint = Color.White)
                    }
                }

                // Task text
                Text(
                    text = task.text,
                    color = if (task.completed) Color(0xFF7A7A75) else Color(0xFF3D3D3A),
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1f),
                    style = if (task.completed)
                        LocalTextStyle.current.copy(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough)
                    else LocalTextStyle.current
                )

                // Icon badge
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(if (task.completed) theme.primary.copy(alpha = 0.12f) else Color(0xFFFAFAF8)),
                    contentAlignment = Alignment.Center
                ) {
                    TaskIconImage(task.icon, modifier = Modifier.size(14.dp), tint = theme.primary)
                }

                // Edit
                IconButton(
                    onClick = { isEditing = true; editText = task.text },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF7A7A75), modifier = Modifier.size(16.dp))
                }

                // Delete
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFD4183D), modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}
