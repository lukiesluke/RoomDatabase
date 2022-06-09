package com.lwg.roomdatabase.service

import com.lwg.roomdatabase.model.Movie
import com.lwg.roomdatabase.model.SpssCra
import com.lwg.roomdatabase.model.SpssRec
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiRequest {

    @GET("movielist.json")
    suspend fun getResultMovieResponse(): Response<List<Movie>>

    @GET("movielist.json")
    fun getResultMovieCall(): Call<List<Movie>>

    @POST("cs2/ma/helo")
    fun getHello(@Body spssCra: SpssCra?): Call<SpssCra>

    companion object {
//        private const val BASE_URL = "https://howtodoandroid.com/"
        private const val BASE_URL = "https://cspuat.pccw.com"

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