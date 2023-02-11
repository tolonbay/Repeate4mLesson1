package com.example.repeate4mlesson1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SessionDao {
    @Insert
    suspend fun save(item: SessionEntity)
    @Query("Select * FROM session")

    suspend fun get():SessionEntity?
}