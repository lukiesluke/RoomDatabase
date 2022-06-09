package com.lwg.roomdatabase.viewModel

import Utils
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.lwg.roomdatabase.R
import com.lwg.roomdatabase.model.Movie
import com.lwg.roomdatabase.model.SpssCra
import com.lwg.roomdatabase.model.SpssRec
import com.lwg.roomdatabase.repository.ApiRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiViewModel(application: Application, private val repository: ApiRepository) :
    AndroidViewModel(application) {
    val context: Context by lazy { application.applicationContext }
    val movieList = MutableLiveData<List<Movie>>()

    fun sayHello() {
        val spssRec = SpssRec().apply {
            bni = "Y"
            dervRid = 0
            devId =
                "fBHqx-eYQIe8DjRXcaKGbp:APA91bH9uHSp_gCiESubx8R7qiEJb94mKUZGhsvY3BWopN0mOMo2K5DMKJ1Z4NygjiQ-4XQsHRaFBc7MXnxoL5G-IyuKfh4BlZRn1c3gwLMcSQfVsq9uWh8Xrey2WEn-mKQD51GQwUct"
            devTy = "A"
            gni = "Y"
            lang = "en"
        }

        val spssCra = SpssCra().apply {
            iLoginI = "testingsr001@gmail.com"
            iCkSum = "1b202bd33308a6d7606046f8c72e7bc30f4ad2c9e1e78abf47415b55d116df4a"
            iSpssRec = spssRec
            serverTS = context.getString(R.string.app_name)
            reply = null
            iApi = "Hello"
            iGwtGud = ""
        }

        val call = repository.sayHello(spssCra)
        call.enqueue(object : Callback<SpssCra> {
            override fun onResponse(call: Call<SpssCra>, response: Response<SpssCra>) {
                response.headers().values("Set-Cookie").toString().split(";").map {
                    if (it.contains("JSESSIONID")) {
                        val c = it.split("=")[1]
                        Utils.setPref(context, "Cookie-set", "c")
                        Log.d("lwg", "cookie: $c")
                    }
                }
            }

            override fun onFailure(call: Call<SpssCra>, t: Throwable) {
                Log.d("lwg", "Error: " + t.message)
            }
        })
    }

    // Response expected
    fun getListResponse() = viewModelScope.launch {
        try {
            val response = repository.getResultMovieResponse()
            if (response.isSuccessful) {
                movieList.postValue(response.body())
            } else {
                Log.d("lwg", "Error")
            }
        } catch (e: Exception) {
            Log.d("lwg", "Error: " + e.message)
        }
    }

    // With Callback expected
    fun getResultTracksCall() = viewModelScope.launch {

        val response = repository.getResultMovieCall()
        response.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(
                call: Call<List<Movie>>,
                response: Response<List<Movie>>
            ) {
                movieList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Log.d("lwg", "Error: " + t.message)
            }
        })
    }
}

class MyAndroidViewModelFactory(
    private val application: Application, private val repository: ApiRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ApiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ApiViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}