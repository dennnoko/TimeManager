package com.example.timemanager.ui.theme

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.timemanager.room_components.DayDatabase
import com.example.timemanager.room_components.TimeDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TimeMeasurementScreen(navController: NavController, db: DayDatabase) {
    //時刻を取得
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
    val sdf = SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault())
    var formattedDate = sdf.format(Date(currentTime))

    //ストップウォッチ用
    var startTime: Long by remember { mutableStateOf(0) }
    var ansTime: Long by remember { mutableStateOf(0) }
    var btnTxt by remember { mutableStateOf("start") }
    //スタートストップ切り替え
    var startStop by remember { mutableStateOf(false) }
    //結果表示切り替え
    var ans by remember { mutableStateOf("00:00:00") }
    //時間フォーマット
    val swf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    //database
    var doing: String by remember { mutableStateOf("") }

    //データ追加の為のコルーチン
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(currentTime) {
        while (true) {
            currentTime = System.currentTimeMillis()

            if (startStop == true) {
                ansTime = currentTime - startTime
                ans = swf.format(ansTime)
            }

            delay(1000)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
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

        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (btnTxt == "start") {
                    btnTxt = "end"
                    startTime = currentTime
                    startStop = true
                    ans = "00:00:00"
                } else {
                    btnTxt = "start"
                    startStop = false

                    //記録する
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            db.TimeDataDao().insertData(TimeDataEntity(timeData = ansTime, doing = doing))
                        }
                    }
                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(Color.Gray),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
        ) {
            Text(text = btnTxt)
        }
        Spacer(modifier = Modifier.height(20.dp))

        AnsWindow(ans = ans, ansTime = ansTime)
        Spacer(modifier = Modifier.height(20.dp))

        Divider()
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedCard(border = BorderStroke(2.dp, Color(0xff00f0f0))) {
            val radioOptions = listOf("プログラミング", "勉強", "ゲーム", "食事", "その他")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[4]) }

            Column() {
                radioOptions.forEach {text ->
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    onOptionSelected(text)
                                }
                            )
                            .padding(horizontal = 16.dp)
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                doing = text
                            }
                        )

                        Text(
                            text = text,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 5.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {navController.navigate("SelectScreen")}) {
            Text(text = "to SelectScreen")
        }
    }
}

@Composable
fun AnsWindow(ans: String, ansTime: Long) {
    OutlinedCard(
        border = BorderStroke(2.dp, Color(0xff00f0f0))
    ) {
        Text(
            text = ans,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(20.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))

        //保存用の時間（秒）
        var ansTimeFormatted = ansTime / 1000
    }
}

@Preview
@Composable
fun PreviewTimeMeasuremetScreen() {
    val navController = rememberNavController()

    TimeMeasurementScreen(navController = navController, db = DayDatabase.getDatabase(LocalContext.current.applicationContext))
}