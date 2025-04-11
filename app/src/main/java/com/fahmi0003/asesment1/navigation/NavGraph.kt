package com.fahmi0003.asesment1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fahmi0003.asesment1.screen.AboutScreen
import com.fahmi0003.asesment1.screen.AddTaskScreen
import com.fahmi0003.asesment1.screen.MainScreen
import com.fahmi0003.asesment1.screen.TaskDetailsScreen

@Composable
fun SetUpNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }

        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }

        composable(route = Screen.AddTask.route) {
            AddTaskScreen(
                navController = navController,
                onTaskAdded = { taskName ->
                    println("Task added: $taskName")
                }
            )
        }
        composable(
            route = Screen.TaskDetails.route,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            TaskDetailsScreen(navController = navController, taskId = taskId)
        }
    }
}
