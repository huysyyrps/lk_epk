package com.example.lk_epk.activity

import com.example.lk_epk.adapter.ImageAdapter
import android.os.Bundle
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.example.lk_epk.data.BannerData
import com.example.lk_epk.util.BaseActivity
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.activity_welcome.*

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
