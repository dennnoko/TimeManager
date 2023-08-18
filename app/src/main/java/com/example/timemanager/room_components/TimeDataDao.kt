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
    //特定のデータ削除
    @Delete
    suspend fun delete(timeData: TimeDataEntity)
    @Query("select * from TDE")
    fun getAll(): Flow<List<TimeDataEntity>>

    //doingのリストを返す
    @Query("SELECT DISTINCT doing FROM TDE")
    suspend fun getDistinctDoingList(): List<String>

    //何をしていたかを指定し、時間の合計を返す
    @Query("SELECT SUM(timeData) FROM TDE WHERE doing = :doing")
    suspend fun getTotalTimeByDoing(doing: String): Int
}