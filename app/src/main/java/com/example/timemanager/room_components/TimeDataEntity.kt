package com.example.timemanager.room_components

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TDE")
data class TimeDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("timeData") val timeData: Int,
    @ColumnInfo("doing") val doing: String
)

/*
時間を記録するテーブルの構造を定義している。
PrimaryKeyはidとし、データが持つ情報は作業時間と作業内容となっている。
 */