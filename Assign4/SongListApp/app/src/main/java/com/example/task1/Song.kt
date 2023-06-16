package com.example.task1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    var category: String,
    var ImageSrc: Int,
    var title: String,
    var genre: String,
    var website: String,
    var releasedDate: String,
    var artist: String):Parcelable {}

