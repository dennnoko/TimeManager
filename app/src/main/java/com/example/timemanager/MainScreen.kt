package com.example.timemanager

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.timemanager.roomTodoList.TodoDatabase
import com.example.timemanager.room_components.DayDatabase
import com.example.timemanager.UIcomponents.DataScreen
import com.example.timemanager.ui.theme.TimeMeasurementScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val db = DayDatabase.getDatabase(context = LocalContext.current.applicationContext)

    val todoDB = TodoDatabase.getDatabase(context = LocalContext.current.applicationContext)

    NavHost(navController = navController, startDestination = "SelectScreen") {
        composable("SelectScreen") { SelectScreen(navController, db) }
        composable("TimeMeasurementScreen") { TimeMeasurementScreen(navController, db, todoDB) }
        composable("DataScreen") { DataScreen(navController, db, todoDB) }
    }
}

/*
NavigationComposeを利用して画面遷移を管理する。遷移先の画面でデータベースのインスタンスが必要になるので、アプリで必ず実行されるここでインスタンスを生成して各Activityに引数として渡す形にした。
routeの命名についてはスクリーンを表すファイル名をそもまま利用した。
 */