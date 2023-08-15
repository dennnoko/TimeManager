package com.example.timemanager.room_components

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TDE")
data class TimeDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("timeData") val timeData: Long,
    @ColumnInfo("doing") val doing: String
)