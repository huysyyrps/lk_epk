package com.example.lk_epk.entity

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class BackData(val path:String, val bitmap:Bitmap): Parcelable,Serializable {
}