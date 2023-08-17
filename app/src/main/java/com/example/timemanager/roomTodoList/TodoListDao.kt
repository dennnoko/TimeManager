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
    //全データ削除
    @Delete
    suspend fun deleteAll(todoList: List<TodoEntity>)
    //リスト取得
    @Query("select * from TodoE")
    fun getAll(): Flow<List<TodoEntity>>
}