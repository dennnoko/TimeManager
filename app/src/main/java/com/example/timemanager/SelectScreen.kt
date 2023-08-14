package com.example.timemanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun SelectScreen(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(onClick = { navController.navigate("TimeMeasurementScreen") }) {
            Text(text = "to TimeMeasurementScreen")
        }
        
        Button(onClick = { navController.navigate("DataScreen") }) {
            Text(text = "to DataScreen")
        }
    }
}