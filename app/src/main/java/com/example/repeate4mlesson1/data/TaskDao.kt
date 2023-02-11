package com.example.repeate4mlesson1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query




    @Dao
    interface TaskDao{
        @Insert
        fun insert(task: TaskEntity)

        @Query("SELECT * FROM task")
        fun getAll():List<TaskEntity>

        @Query("DELETE FROM task WHERE id = :taskId")
        fun deleteBy(taskId: Long)

        @Query("SELECT * FROM task ORDER BY id DESC")
        fun getAllSortByTime(): List<TaskEntity>

        @Query("SELECT * FROM task ORDER BY title ASC")
        fun getAllSortByLetter(): List<TaskEntity>
    }
