package com.example.shoppinglist
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class item (
    var id: Int,
    var icon: Int,
    var name: String,
    var detail: String,
    var qty: String,
    var size: String,
    var date: String):Parcelable
{
    constructor(icon: Int, name: String, detail: String, qty: String, size: String, date: String): this (-1, icon, name, detail, qty, size, date)
}
