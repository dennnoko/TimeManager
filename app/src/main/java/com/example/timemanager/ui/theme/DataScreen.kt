package com.example.timemanager.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.timemanager.room_components.DayDatabase
import com.example.timemanager.room_components.TimeDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DataScreen(navController: NavController, dayDatabase: DayDatabase) {
    var dataList by remember { mutableStateOf(emptyList<TimeDataEntity>()) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            dayDatabase.TimeDataDao().getAll().collect() {
                dataList = it
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("SelectScreen") }) {
            Text(text = "to SelectScreen")
        }
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedCard(border = BorderStroke(3.dp, color = Color(0xff00f0f0))) {
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                items(dataList) {data ->
                    val formattedData = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(data.timeData)
                    
                    Row() {
                        Text(text = data.doing)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = formattedData)
                    }
                }
            }
        }
    }
}