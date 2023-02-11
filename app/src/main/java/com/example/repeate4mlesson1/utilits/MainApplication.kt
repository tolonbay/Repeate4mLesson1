package com.example.repeate4mlesson1.utilits

import android.app.Application
import androidx.room.Room

class MainApplication:Application() {

    override fun onCreate() {
        super.onCreate()

        appDatabase = Room.databaseBuilder(
            this,
        AppDatabase::class.java, "task_db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    }
    companion object{
        var appDatabase: AppDatabase? = null
    }
}
