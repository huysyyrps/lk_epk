package com.example.lk_epk.data

import com.example.lk_epk.R


object BannerData {
    fun setBannerData(): ArrayList<Int> {
        val bannerList = ArrayList<Int>()
        bannerList.apply {
            add(R.drawable.banner1)
            add(R.drawable.banner2)
            add(R.drawable.banner3)
            add(R.drawable.banner4)
            add(R.drawable.banner5)
        }
        return bannerList
    }
}
