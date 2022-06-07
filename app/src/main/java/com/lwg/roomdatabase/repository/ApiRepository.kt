package com.lwg.roomdatabase.repository

import com.lwg.roomdatabase.service.ApiRequest

class ApiRepository constructor(private var apiRequest: ApiRequest) {

    suspend fun getResultMovieResponse() = apiRequest.getResultMovieResponse()

    fun getResultMovieCall() = apiRequest.getResultMovieCall()
}


