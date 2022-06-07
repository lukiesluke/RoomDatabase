package com.lwg.roomdatabase.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lwg.roomdatabase.model.Movie
import com.lwg.roomdatabase.repository.ApiRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiViewModel(private val repository: ApiRepository) : ViewModel() {
    val movieList = MutableLiveData<List<Movie>>()

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

class ApiModelFactory constructor(private val repository: ApiRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ApiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            ApiViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}