package com.example.lk_epk.entity

import com.github.mikephil.charting.data.Entry

data class ScanABean(
    var gate : String,
    var leave : String,
    var materialType : String,
    var audioSpeed : String,
    var waveType : String,
    var rangeAdd : String,
    var workTemp : String,
    var landList: ArrayList<Entry>
)
