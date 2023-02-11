package com.example.repeate4mlesson1.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey(autoGenerate = false)
    val phoneNumber:String
)
