package com.example.lk_epk.util

object DataHodel {
    private var pathList = ArrayList<String>()
    fun setDataList(dataList:ArrayList<String>){
        pathList = dataList
    }

    fun getDataList():ArrayList<String>{
        return pathList
    }
}