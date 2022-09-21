package com.michaelau.tasktraker.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myTask")
data class Task (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title:String,
    val content:String,
    val startDate: String,
    val startTime: String,
    val repeatType:String,
    val start_watch:String,
    val stop_watch:String,
    val colorTheme:Int,
    val check:Boolean?=false
    )