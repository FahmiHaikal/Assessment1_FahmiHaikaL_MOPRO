package com.fahmi0003.asesment1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fahmi0003.asesment1.screen.AboutScreen
import com.fahmi0003.asesment1.screen.AddTaskScreen
import com.fahmi0003.asesment1.screen.MainScreen
import com.fahmi0003.asesment1.screen.TaskDetailsScreen

@Composable
fun SetUpNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(route = Screen.Home.route) {
            MainScreen(navController = navController)
        }

        composable(route = Screen.About.route) {
            AboutScreen(navController = navController)
        }

        composable(route = Screen.AddTask.route) {
            AddTaskScreen(navController = navController)
        }

        composable(
            route = Screen.TaskDetails.route,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: "Tidak Diketahui"
            TaskDetailsScreen(navController = navController, taskId = taskId)
        }
    }
}
