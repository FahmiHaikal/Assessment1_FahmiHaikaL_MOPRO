package com.fahmi0003.asesment1.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fahmi0003.asesment1.R
import com.fahmi0003.asesment1.ui.theme.AppTheme

@Composable
fun AddTaskScreen(
    navController: NavHostController? = null,
    onTaskAdded: (String) -> Unit = {}
) {
    val taskName = remember { mutableStateOf("") }
    val isError = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = taskName.value,
            onValueChange = {
                taskName.value = it
                isError.value = false
            },
            label = { Text(text = stringResource(R.string.task_placeholder)) },
            isError = isError.value,
            singleLine = true,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxSize(fraction = 0.8f)
        )
        if (isError.value) {
            Text(text = stringResource(R.string.no_tasks), color = Color.Red)
        }
        Button(
            onClick = {
                if (taskName.value.isNotBlank()) {
                    onTaskAdded(taskName.value)
                    taskName.value = ""
                    navController?.popBackStack()
                } else {
                    isError.value = true
                }
            }
        ) {
            Text(text = stringResource(R.string.add_task))
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AddTaskScreenPreview() {
    AppTheme {
        val previewNavController = rememberNavController()
        AddTaskScreen(navController = previewNavController) { taskName ->
            println("Task added: $taskName")
        }
    }
}
