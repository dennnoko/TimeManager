package com.example.timemanager.roomTodoList

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun TodoDao(): TodoListDao

    companion object {
        private var todoDatabase: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            if(todoDatabase == null) {
                todoDatabase = Room.databaseBuilder(
                    context = context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                ).build()
            }
            return todoDatabase!!
        }
    }
}

/*
作業内容のデータベース
データベースを作成するメソッドが定義されている
 */