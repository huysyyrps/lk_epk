package com.example.lk_epk.activity

import adapter.ImageAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.example.lk_epk.data.BannerData
import com.example.lk_epk.util.BaseActivity
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.activity_welcome.*
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class WelcomeActivity : BaseActivity() {
    var i:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val imgList = BannerData.setBannerData()
        banner.apply {
            setIndicator(CircleIndicator(MyApplication.context))
                .setAdapter(ImageAdapter(imgList), true)
        }
        btnIn.setOnClickListener {
            MainActivity.actionStart(this)
            finish()
        }
    }
}
