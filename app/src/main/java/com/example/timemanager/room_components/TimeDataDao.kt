package com.example.timemanager.room_components

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeDataDao {
    //データ追加
    @Insert
    suspend fun insertData(timeData: TimeDataEntity)
    //全データ削除
    @Delete
    suspend fun deleteAll(dataList: List<TimeDataEntity>)
    //リスト入手
    @Query("select * from TDE")
    fun getAll(): Flow<List<TimeDataEntity>>
}