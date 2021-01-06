package com.example.cooldogdemo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ItemClass(
    val title: String, val content: String,val image:Int,val music:String
    ): Parcelable