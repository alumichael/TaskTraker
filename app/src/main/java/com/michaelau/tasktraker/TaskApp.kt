package com.michaelau.tasktraker

import android.app.Application
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TaskApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

}