package com.example.task2
import retrofit2.Call
import retrofit2.http.GET

interface ImageApi {
    @GET("includes/Constants.php")
    fun getImages(): Call<ArrayList<image>>  // data in the form of list
}