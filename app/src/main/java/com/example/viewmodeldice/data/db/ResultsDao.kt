package com.example.viewmodeldice.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.viewmodeldice.data.entities.ResultEntity

@Dao
interface ResultsDao {

    @Query("SELECT * FROM RESULTS")
    suspend fun getAllResults(): List<ResultEntity>

    @Insert
    suspend fun insertResult(result: ResultEntity)

    @Delete
    suspend fun deleteResult(result: ResultEntity)

}