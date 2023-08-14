package com.example.timemanager.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun TimeMeasurementScreen(navController: NavController) {

    Column() {

    }
}

@Preview
@Composable
fun PreviewTimeMeasuremetScreen() {
    val navController = rememberNavController()

    TimeMeasurementScreen(navController = navController)
}