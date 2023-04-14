package com.example.lk_epk

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.osard.screen.utils.ScreenRecordUtils

class MyApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        ScreenRecordUtils.init(this);
    }
}