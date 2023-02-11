package com.example.repeate4mlesson1.ui.home.new_task


import androidx.room.*

data class TaskModel(
    val id: Int,
    val title:String,
    val description: String?,
    val pictureUri:String? = null,

    )

