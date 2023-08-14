package com.example.timemanager

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.timemanager.ui.theme.DataScreen
import com.example.timemanager.ui.theme.TimeMeasurementScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "SelectScreen") {
        composable("SelectScreen") { SelectScreen(navController) }
        composable("TimeMeasurementScreen") { TimeMeasurementScreen(navController) }
        composable("DataScreen") { DataScreen(navController) }
    }
}