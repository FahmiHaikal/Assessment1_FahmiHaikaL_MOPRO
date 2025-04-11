package com.fahmi0003.asesment1.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fahmi0003.asesment1.R
import com.fahmi0003.asesment1.navigation.Screen
import com.fahmi0003.asesment1.ui.theme.AppTheme

data class Task(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    var isCompleted: Boolean = false,
    val priority: Priority = Priority.MEDIUM
)

enum class Priority {
    HIGH, MEDIUM, LOW
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(navController: NavHostController? = null) {
    var taskTitle by remember { mutableStateOf("") }
    var taskList by remember { mutableStateOf(listOf<Task>()) }
    var selectedPriority by remember { mutableStateOf(Priority.MEDIUM) }
    var showMenu by remember { mutableStateOf(false) }
    
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = { navController?.navigate(Screen.About.route) }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.about),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Options",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.language)) },
                            leadingIcon = {
                                Icon(Icons.Default.Language, contentDescription = null)
                            },
                            onClick = {
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.share)) },
                            onClick = {
                                shareApp(context)
                                showMenu = false
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.todolist),
                contentDescription = "TodoList Logo",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )
            
            OutlinedTextField(
                value = taskTitle,
                onValueChange = { taskTitle = it },
                label = { Text(text = stringResource(R.string.task_placeholder)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.priority),
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PriorityRadioButton(
                    selected = selectedPriority == Priority.HIGH,
                    priority = Priority.HIGH,
                    onClick = { selectedPriority = Priority.HIGH }
                )
                PriorityRadioButton(
                    selected = selectedPriority == Priority.MEDIUM,
                    priority = Priority.MEDIUM,
                    onClick = { selectedPriority = Priority.MEDIUM }
                )
                PriorityRadioButton(
                    selected = selectedPriority == Priority.LOW,
                    priority = Priority.LOW,
                    onClick = { selectedPriority = Priority.LOW }
                )
            }
            
            Button(
                onClick = {
                    if (taskTitle.isNotBlank()) {
                        taskList = taskList + Task(title = taskTitle, priority = selectedPriority)
                        taskTitle = ""
                        selectedPriority = Priority.MEDIUM
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.add_task))
            }
            
            if (taskList.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_tasks),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(taskList) { task ->
                        TaskRow(
                            task = task,
                            onDelete = {
                                taskList = taskList - task
                            },
                            onToggleComplete = {
                                taskList = taskList.map { 
                                    if (it.id == task.id) it.copy(isCompleted = !it.isCompleted) else it 
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PriorityRadioButton(
    selected: Boolean,
    priority: Priority,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(
            text = when (priority) {
                Priority.HIGH -> stringResource(R.string.high)
                Priority.MEDIUM -> stringResource(R.string.medium)
                Priority.LOW -> stringResource(R.string.low)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskRow(
    task: Task,
    onDelete: () -> Unit,
    onToggleComplete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onToggleComplete
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (task.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = when (task.priority) {
                        Priority.HIGH -> stringResource(R.string.high)
                        Priority.MEDIUM -> stringResource(R.string.medium)
                        Priority.LOW -> stringResource(R.string.low)
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row {
                IconButton(onClick = onToggleComplete) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = stringResource(R.string.complete_task),
                        tint = if (task.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_task)
                    )
                }
            }
        }
    }
}

private fun shareApp(context: Context) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "Check out my awesome TodoList app!")
    }
    context.startActivity(Intent.createChooser(shareIntent, null))
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen(rememberNavController())
    }
}
