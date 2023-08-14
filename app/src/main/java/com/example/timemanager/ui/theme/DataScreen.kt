package com.example.timemanager.ui.theme

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DataScreen(navController: NavController) {
    Button(onClick = { navController.navigate("SelectScreen") }) {
        Text(text = "to SelectScreen")
    }
}