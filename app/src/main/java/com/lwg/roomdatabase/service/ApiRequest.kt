package com.lwg.roomdatabase.service

import com.lwg.roomdatabase.model.Movie
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiRequest {

    @GET("movielist.json")
    suspend fun getResultMovieResponse(): Response<List<Movie>>

    @GET("movielist.json")
    fun getResultMovieCall(): Call<List<Movie>>

    companion object {
        private const val BASE_URL = "https://howtodoandroid.com/"

        var apiRequest: ApiRequest? = null

        // Create the Retrofit service instance using the retrofit.
        fun getInstance(): ApiRequest {
            if (apiRequest == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                apiRequest = retrofit.create(ApiRequest::class.java)
            }
            return apiRequest!!
        }
    }
}