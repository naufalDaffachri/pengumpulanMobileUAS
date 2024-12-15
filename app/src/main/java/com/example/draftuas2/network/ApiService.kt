package com.example.draftuas2.network


import android.graphics.Movie
import com.example.draftuas2.model.Data
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("movie")
    fun getAllUsers(): Call<List<Data>>
    @POST("movie")
    fun addMovie(@Body movie: Data): Call<Data>
    @DELETE("movie/{id}")
    fun deleteMovie(@Path("id") movieId: String): Call<Data>
    @POST("movie/{id}")
    fun updateMovie(@Path("id") movieId: String, @Body requestBody: RequestBody): Call<Data>
}
