package com.example.timemanager

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.timemanager.room_components.DayDatabase
import com.example.timemanager.ui.theme.DataScreen
import com.example.timemanager.ui.theme.TimeMeasurementScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val db = DayDatabase.getDatabase(context = LocalContext.current.applicationContext)

    NavHost(navController = navController, startDestination = "SelectScreen") {
        composable("SelectScreen") { SelectScreen(navController) }
        composable("TimeMeasurementScreen") { TimeMeasurementScreen(navController, db) }
        composable("DataScreen") { DataScreen(navController, db) }
    }
}