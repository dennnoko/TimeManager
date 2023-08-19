package com.example.timemanager.UIcomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timemanager.room_components.DayDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun TotalTimeGraph(db: DayDatabase) {
    //doingのリストを得る
    var doingList: List<String> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            doingList = db.TimeDataDao().getDistinctDoingList()
        }
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .border(BorderStroke(5.dp, Color(0xff00f0f0)), RoundedCornerShape(10.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Total Time",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(8.dp)
            )

            LazyRow() {
                items(doingList) {doing ->

                    //doingの合計時間取得
                    var doingTime by remember { mutableStateOf(0) }
                    var doingTimeH by remember { mutableStateOf(0) }
                    var doingTimeM by remember { mutableStateOf(0) }

                    LaunchedEffect(Unit) {
                        withContext(Dispatchers.IO) {
                            doingTime = (db.TimeDataDao().getTotalTimeByDoing(doing) / 60)
                            doingTimeH = doingTime / 60
                            doingTimeM = doingTime % 60
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(8.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = doing,
                            fontWeight = FontWeight.SemiBold,
                        )

                        Box(
                            modifier = Modifier
                                .size(55.dp, doingTime.dp)
                                .background(Color.Green)
                        )

                        Text(
                            text = doingTimeH.toString() + "h " + doingTimeM.toString() + "m",
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewTotalTimeGraph() {
    Surface {
        TotalTimeGraph(db = DayDatabase.getDatabase(context = LocalContext.current.applicationContext))
    }
}