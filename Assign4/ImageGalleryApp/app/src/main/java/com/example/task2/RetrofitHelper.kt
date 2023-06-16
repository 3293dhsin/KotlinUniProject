package com.example.task2
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitHelper {
    val baseUrl = "http://10.0.2.2/android/"
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())  // convert json to java object
            .build()
    }
}