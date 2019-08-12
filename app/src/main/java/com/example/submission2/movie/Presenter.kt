package com.example.submission2.movie

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.submission2.API.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class Presenter : ViewModel(){
    var movies = ArrayList<ResultsItem>()
    var mutableLiveData = MutableLiveData<List<ResultsItem>>()
    var status= MutableLiveData<Boolean>()

    init {
        data()
    }

    fun data(){
        status.value = true
        val apiService = NetworkConfig.getApiService()
        val call = apiService.getDataMovie()

        call.enqueue(object : Callback<Response> {

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                val data = response.body()
                if (data != null ) {
                    status.value = !response.isSuccessful
                    movies = data.dataResult()!!
                    mutableLiveData.setValue(movies)

                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                status.value = true
            }
        })

    }

    fun setData() : MutableLiveData<List<ResultsItem>> {
        return mutableLiveData
    }
    fun setStatus() : MutableLiveData<Boolean>{
        return  status
    }


}