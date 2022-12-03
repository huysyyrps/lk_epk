package com.example.lk_epk.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParsePosition
import java.text.SimpleDateFormat

object Timestamp {
    /**
     *日期转换
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun transToString(time:Long):String{
        return SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time)
//        SimpleDateFormat("YY-MM-DD-hh-mm-ss").parse(date, ParsePosition(0)).time
    }
}