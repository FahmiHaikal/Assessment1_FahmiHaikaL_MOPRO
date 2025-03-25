package com.fahmi0003.asesment1.navigation

sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object About : Screen("about")
    data object AddTask : Screen("addTask")

    data object TaskDetails : Screen("taskDetails/{taskId}") {
        fun createRoute(taskId: String): String {
            require(taskId.isNotBlank()) { "Task ID cannot be blank" }
            return "taskDetails/$taskId"
        }
    }
}
