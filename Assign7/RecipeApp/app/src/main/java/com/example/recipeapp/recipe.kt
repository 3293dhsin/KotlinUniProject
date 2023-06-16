package com.example.recipeapp
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class recipeList (var hits : ArrayList<Hits>):Parcelable{}
@Parcelize
data class Hits (var recipe : Recipe):Parcelable
@Parcelize
data class Recipe (
    var label : String,
    var images : Images,
    var source : String,
    var dietLabels : ArrayList<String>,
    var ingredientLines : ArrayList<String>,
    var mealType : ArrayList<String>):Parcelable
@Parcelize
data class Images (
    var THUMBNAIL : THUMBNAIL,
    var REGULAR   : REGULAR
):Parcelable
@Parcelize
data class THUMBNAIL (
    var url : String,
    var width : Int,
    var height : Int
):Parcelable
@Parcelize
data class REGULAR (
    var url : String,
    var width : Int,
    var height : Int
):Parcelable