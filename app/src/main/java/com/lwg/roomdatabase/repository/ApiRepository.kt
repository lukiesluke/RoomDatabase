package com.lwg.roomdatabase.repository

import com.lwg.roomdatabase.model.SpssCra
import com.lwg.roomdatabase.service.ApiRequest
import retrofit2.Call

class ApiRepository constructor(private var apiRequest: ApiRequest) {

    suspend fun getResultMovieResponse() = apiRequest.getResultMovieResponse()

    fun getResultMovieCall() = apiRequest.getResultMovieCall()

    fun sayHello(spssCra: SpssCra?): Call<SpssCra> {
        return apiRequest.getHello(spssCra)
    }
}


