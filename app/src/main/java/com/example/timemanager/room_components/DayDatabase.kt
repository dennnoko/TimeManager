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