package com.example.timemanager.roomTodoList

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TodoE")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("todo") val todo: String,
)

/*
作業項目を保存するテーブル。
作業名のみ保存できれば良いのでidを自動生成してPrimaryKeyにする必要があるか迷ったが、追加しようとした作業名が重複している場合の処理のことなどを考えたらとりあえずid管理にしようということになった。
その結果同名の作業内容が追加可能になってしまっている。
 */