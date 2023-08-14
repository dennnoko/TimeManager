package com.example.timemanager.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TimeMeasurementScreen(navController: NavController) {
    //時刻を取得
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
    val sdf = SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault())
    var formattedDate = sdf.format(Date(currentTime))

    LaunchedEffect(currentTime) {
        while (true) {
            currentTime = System.currentTimeMillis()

            delay(1000)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        OutlinedCard(border = BorderStroke(2.dp, Color(0xff00f0f0))) {
            Text(
                text = "Current Time",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp)
            )

            Text(
                text = formattedDate,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewTimeMeasuremetScreen() {
    val navController = rememberNavController()

    TimeMeasurementScreen(navController = navController)
}