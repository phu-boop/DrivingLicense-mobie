package com.example.drivinglicence.app.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemRoadTraffic(val title: String, val content: String, val thumb: Int):Parcelable
