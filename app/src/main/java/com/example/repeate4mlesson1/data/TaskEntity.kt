package com.example.repeate4mlesson1.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



    @Entity(tableName = "task")
    data class TaskEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        @ColumnInfo(name = "title")
        val title:String,
        @ColumnInfo(name = "desc")
        val description: String? = null,
        @ColumnInfo(name = "picture_uri")
        val pictureUri: String? = null,

        )
