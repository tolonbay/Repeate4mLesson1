package com.example.repeate4mlesson1.utilits

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.repeate4mlesson1.data.SessionDao
import com.example.repeate4mlesson1.data.SessionEntity
import com.example.repeate4mlesson1.data.TaskDao
import com.example.repeate4mlesson1.data.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        SessionEntity::class
        ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val sessionDao: SessionDao

}