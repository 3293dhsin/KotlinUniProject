package com.example.recipeapp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {
    @GET("api/recipes/v2")
    fun getRecipe(
        @Query("type") type: String,
        @Query("q") q: String?,
        @Query("app_id") app_id: String,
        @Query("app_key") app_key: String
    ) : Call<recipeList>
}