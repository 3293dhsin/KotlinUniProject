package com.example.task1

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cafe(
    val ImageIcon: Int,
    val Image: Int,
    val Name: Int,
    val Address: String,
    val OpenHours: String,
    val cuisine: List<String>,
    val rate: String,
    val maxDinner: Int):Parcelable { }

