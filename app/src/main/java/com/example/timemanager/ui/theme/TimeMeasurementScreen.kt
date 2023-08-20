package com.example.timemanager.ui.theme

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.timemanager.roomTodoList.TodoDatabase
import com.example.timemanager.roomTodoList.TodoEntity
import com.example.timemanager.room_components.DayDatabase
import com.example.timemanager.room_components.TimeDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeMeasurementScreen(navController: NavController, db: DayDatabase, todoDB: TodoDatabase) {
    //時刻を取得
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
    val sdf = SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault())
    var formattedDate = sdf.format(Date(currentTime))

    //ストップウォッチ用
    var startTime: Long by remember { mutableStateOf(0) }
    var ansTime: Int by remember { mutableStateOf(0) }
    var btnTxt by remember { mutableStateOf("start") }
    //スタートストップ切り替え
    var startStop by remember { mutableStateOf(false) }
    //結果表示切り替え
    var ans by remember { mutableStateOf("00:00:00") }
    //時間
    var ansS: Int by remember { mutableStateOf(0) }
    var ansM: Int by remember { mutableStateOf(0) }
    var ansH: Int by remember { mutableStateOf(0) }

    //database
    var doing: String by remember { mutableStateOf("その他") }

    //データ追加の為のコルーチン
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(currentTime) {
        while (true) {
            currentTime = System.currentTimeMillis()

            if (startStop == true) {
                ansTime = ((currentTime - startTime)/1000).toInt()
                ansS = ansTime % 60
                ansM = (ansTime/60) % 60
                ansH = (ansTime/3600) % 24

                ans = String.format("%02d", ansH) + ":" + String.format("%02d", ansM) + ":" + String.format("%02d", ansS)
            }

            delay(1000)
        }
    }

    //doingのリストを取得
    var todoList by remember { mutableStateOf(emptyList<String>()) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            todoDB.TodoDao().getAllName().collect() {
                todoList = it
            }
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
            colors = ButtonDefaults.buttonColors(Color(0xff00f0f0)),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
        ) {
            Text(
                text = btnTxt,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        AnsWindow(ans = ans, ansTime = ansTime)
        Spacer(modifier = Modifier.height(20.dp))

        Divider()
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedCard(
            border = BorderStroke(2.dp, Color(0xff00f0f0)),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .width(250.dp)
                .height(230.dp)
        ) {
            val radioOptions = todoList
            //val radioOptions = listOf("A","B","C")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf<String?>(null) }

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                radioOptions.forEach {text ->
                    Row(
                        verticalAlignment = CenterVertically,
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
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xff00f0f0),
                                unselectedColor = Color.Black,
                            ),
                            onClick = {
                                onOptionSelected(text)
                                doing = text
                            },
                            modifier = Modifier
                                .padding(3.dp)
                        )

                        Text(
                            text = text,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
        //doingを追加するボタン
        var txt by remember { mutableStateOf("") }

        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .clickable {
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            if (txt != "") {
                                todoDB.TodoDao().insertNewTodo(TodoEntity(todo = txt))

                                txt = ""
                            }
                        }
                    }
                }
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "add",
                modifier = Modifier
            )

            OutlinedTextField(
                value = txt,
                onValueChange = {newTxt ->
                    txt = newTxt
                },
                singleLine = true,
                label = {
                    Text(text = "Start something new")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                if (txt != "") {
                                    todoDB.TodoDao().insertNewTodo(TodoEntity(todo = txt))

                                    txt = ""
                                }
                            }
                        }
                    }
                ),
                modifier = Modifier
                    .padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {navController.navigate("SelectScreen")},
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(Color(0xff00f0f0))
        ) {
            Text(
                text = "Back",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun AnsWindow(ans: String, ansTime: Int) {
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

    TimeMeasurementScreen(
        navController = navController,
        db = DayDatabase.getDatabase(LocalContext.current.applicationContext),
        todoDB = TodoDatabase.getDatabase(LocalContext.current.applicationContext)
    )
}

/*
時間計測画面
特に何も考えずに思いつくまま機能を追加していった結果とても見にくいコードになってしまった。
この画面に実装した機能は大きく2つで、時間の計測と作業内容のリストへの新規項目の追加となっている。
 */