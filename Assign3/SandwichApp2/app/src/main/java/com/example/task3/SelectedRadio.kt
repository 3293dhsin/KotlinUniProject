package com.example.task3

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectedRadio(var opt: String, var price: Double):Parcelable {}
