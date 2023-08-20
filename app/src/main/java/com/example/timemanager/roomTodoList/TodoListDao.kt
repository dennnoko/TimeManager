package com.example.timemanager.roomTodoList

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {
    //追加
    @Insert
    suspend fun insertNewTodo(todo: TodoEntity)
    //選んで削除
    @Delete()
    suspend fun deleteTodo(todo: TodoEntity)
    //IDで選択して削除
    @Query("delete from TodoE where id = :id")
    suspend fun deleteTodoById(id: Int)
    //全データ削除
    @Delete
    suspend fun deleteAll(todoList: List<TodoEntity>)
    //リスト取得
    @Query("select * from TodoE")
    fun getAll(): Flow<List<TodoEntity>>
    //リストのStringのみ取得
    @Query("select distinct todo from TodoE")
    fun getAllName(): Flow<List<String>>
}

/*
作業項目についてのデータベースの処理をまとめたDao
deleteTodoやdeleteAllなど、多分どこでも使用していない物を用意してしまっている。気が向いたら使用されていないか確認して消す。
 */