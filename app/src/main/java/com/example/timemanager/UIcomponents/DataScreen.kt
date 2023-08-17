package com.example.timemanager.UIcomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DataScreen(navController: NavController, dayDatabase: DayDatabase, todoDB: TodoDatabase) {
    var dataList by remember { mutableStateOf(emptyList<TimeDataEntity>()) }
    val coroutineScope = rememberCoroutineScope()
    var todoList by remember { mutableStateOf(emptyList<TodoEntity>()) }
    //データ削除の為
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            dayDatabase.TimeDataDao().getAll().collect() {
                dataList = it
            }

            todoDB.TodoDao().getAll().collect() {
                todoList = it
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { navController.navigate("SelectScreen") },
            colors = ButtonDefaults.buttonColors(Color(0xff00f0f0)),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = "to SelectScreen",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedCard(
            border = BorderStroke(3.dp, color = Color(0xff00f0f0)),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .size(250.dp, 150.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                items(dataList) {data ->
                    Row() {
                        Text(
                            text = data.doing,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .size(120.dp, 20.dp)
                        )

                        val ansS = data.timeData % 60
                        val ansM = (data.timeData/60) % 60
                        val ansH = (data.timeData/3600)

                        Text(
                            text = String.format("%02d", ansH) + ":" + String.format("%02d", ansM) + ":" + String.format("%02d", ansS),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .size(70.dp, 20.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        dayDatabase.TimeDataDao().deleteAll(dataList = dataList)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(Color.Red),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(
                text = "Delete all data",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Divider()
        Spacer(modifier = Modifier.height(10.dp))

        //選択されたもののidを保持する変数
        var deleteId by remember { mutableStateOf(0) }
        
        OutlinedCard(
            border = BorderStroke(3.dp, Color(0xff00f0f0)),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .size(250.dp, 150.dp)
        ) {
            //RadioButtonの選択されたものを保持する変数
            var selectedOption by remember { mutableStateOf("") }
            //このスコープ内にdoingのリストを表示し、選択できるようにする
            //LazyColumnで取得したtodoListの内容を表示することも考えたが、どれが選択状態なのかの見分けが付きにくくUIとして良くないのでRadioButtonを使いたい
            Column() {
                todoList.forEach { todo ->
                    RadioButton(
                        selected = selectedOption == todo.todo, 
                        onClick = { 
                            selectedOption = todo.todo
                            deleteId = todo.id
                        }
                    )

                    Text(text = todo.todo)
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // ↑で選択したアイテムの削除ボタン
        Button(
            onClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        //削除のために
                        todoDB.TodoDao().deleteTodoById(deleteId)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(text = "Delete selected item", color = Color.Black)
        }
    }
}

@Preview
@Composable
fun PreviewDataScreen() {
    Surface() {
        val navController = rememberNavController()
        val dayDatabase = DayDatabase.getDatabase(LocalContext.current.applicationContext)
        val todoDB = TodoDatabase.getDatabase(LocalContext.current.applicationContext)
        DataScreen(navController , dayDatabase, todoDB)
    }
}