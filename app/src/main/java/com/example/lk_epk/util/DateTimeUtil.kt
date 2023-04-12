package com.example.lk_epk.util

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {
    //获取当前完整的日期和时间
    fun getNowDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date())
    }

    //获取当前时间
    fun getNowTime(): String {
        val sdf = SimpleDateFormat("HH:mm:ss")
        return sdf.format(Date())
    }

    //获取当前时间（精确到毫秒）
    fun getNowTimeDetail(): String {
        val sdf = SimpleDateFormat("HH:mm:ss.SSS")
        return sdf.format(Date())
    }
}