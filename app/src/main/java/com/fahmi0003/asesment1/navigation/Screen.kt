package com.fahmi0003.asesment1.navigation

sealed class Screen(val route: String) {
    data object About : Screen("about")
    data object Home : Screen("home")
    data object AddTask : Screen("add_task")
    data object TaskDetails : Screen("task_details/{taskId}")
    
    fun createTaskDetailsRoute(taskId: String): String {
        return "task_details/$taskId"
    }
}
