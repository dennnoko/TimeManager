package com.example.timemanager

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.timemanager.UIcomponents.TotalTimeGraph
import com.example.timemanager.room_components.DayDatabase

@Composable
fun SelectScreen(navController: NavController, db: DayDatabase) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        
        Image(
            painter = painterResource(id = R.drawable.clock_icon),
            contentDescription = "null",
            modifier = Modifier
                .size(250.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { navController.navigate("TimeMeasurementScreen") },
            colors = ButtonDefaults.buttonColors(Color(0xff00f0f0)),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 80.dp, end = 80.dp)
        ) {
            Text(
                text = "Measurement",
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        
        Button(
            onClick = { navController.navigate("DataScreen") },
            colors = ButtonDefaults.buttonColors(Color(0xff00f0f0)),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 80.dp, end = 80.dp)
        ) {
            Text(
                text = "Data management",
                color = Color.Black
            )
        }
        
        TotalTimeGraph(db = db)
    }
}

@Preview
@Composable
fun PreviewSelectScreen() {
    val navController = rememberNavController()
    val db = DayDatabase.getDatabase(LocalContext.current.applicationContext)

    Surface() {
        SelectScreen(navController, db)
    }
}

/*
もともとはここでデータの記録とデータの確認を選択して、それぞれのスクリーンに遷移する予定だったが、データにはもっと容易にアクセスできたほうが良いという考えから、このActivity内にデータのグラフを表示する形にした。
グラフを表示するコンポーザブル関数は別で定義してある。
 */