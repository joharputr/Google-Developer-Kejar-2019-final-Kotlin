package com.example.submission2.TVshow

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.submission2.API.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import java.util.ArrayList

class PresenterTV : ViewModel(){
    var movies = ArrayList<ResultsItemTv>()
    var mutableLiveData = MutableLiveData<List<ResultsItemTv>>()
    var status= MutableLiveData<Boolean>()
    private var movieResults: List<ResultsItemTv>? = ArrayList<ResultsItemTv>()

    init {
        data()
    }

    fun data(){
        status.value = true
        val apiService = NetworkConfig.getApiService()
        val call = apiService.getDataTV()

        call.enqueue(object : Callback<ResponseTV> {

            override fun onResponse(call: Call<ResponseTV>, response: retrofit2.Response<ResponseTV>) {
                val data = response.body()
                if (data != null ) {
                    status.value = !response.isSuccessful
                    movies = data.results as ArrayList<ResultsItemTv>
                    mutableLiveData.setValue(movies)

                }
            }

            override fun onFailure(call: Call<ResponseTV>, t: Throwable) {
                status.value = true
            }
        })

    }

    fun setData() : MutableLiveData<List<ResultsItemTv>> {
        return mutableLiveData
    }
    fun setStatus() : MutableLiveData<Boolean> {
        return  status
    }


}