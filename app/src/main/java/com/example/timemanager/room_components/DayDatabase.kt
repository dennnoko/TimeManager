package com.example.timemanager.room_components

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TimeDataEntity::class], version = 1, exportSchema = false)
abstract class DayDatabase: RoomDatabase() {
    abstract fun TimeDataDao(): TimeDataDao

    companion object {
        private var dayDatabase: DayDatabase? = null

        fun getDatabase(context: Context): DayDatabase {
            if (dayDatabase == null) {
                dayDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    DayDatabase::class.java,
                    "my_app_database"
                ).build()
            }
            return dayDatabase!!
        }
    }
}

/*
DayDatabaseという名前は、もともとは1日の行動記録を付けるアプリにしようとしていたから。実際には特に期間は指定しておらず、ユーザーが任意のタイミングでデータをリセットできる仕様となった。
ここではデータベースクラスの作成のメソッドが定義してあり、データベースが作成されていない場合のみデータベースを作成し、作成済みであれば既存のデータベースを返す。
 */