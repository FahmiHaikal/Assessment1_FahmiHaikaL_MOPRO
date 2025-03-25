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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fahmi0003.asesment1.ui.theme.Asesment1Theme

@Composable
fun AddTaskScreen(
    navController: NavHostController? = null,
    onTaskAdded: (String) -> Unit = {}
) {
    val taskName = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = taskName.value,
            onValueChange = { taskName.value = it },
            label = { Text(text = "Nama Tugas") },
            singleLine = true,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxSize(fraction = 0.8f) // Lebar relatif
        )

        Button(
            onClick = {
                if (taskName.value.isNotBlank()) {
                    onTaskAdded(taskName.value) // Trigger the callback
                    taskName.value = "" // Reset input
                    navController?.popBackStack() // Kembali ke layar sebelumnya
                }
            }
        ) {
            Text(text = "Tambah Tugas")
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AddTaskScreenPreview() {
    Asesment1Theme {
        AddTaskScreen { taskName ->
            println("Task added: $taskName")
        }
    }
}
