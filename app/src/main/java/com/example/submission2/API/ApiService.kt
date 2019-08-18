package com.example.submission2.API

import com.example.submission2.TVshow.ResponseTV
import com.example.submission2.movie.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @GET("3/discover/movie?api_key=9b3efc88fed3cb0e25a0849788f05166&language=en-US")
    @Headers("Content-Type:application/json")
    fun getDataMovie(): Call<Response>


    @GET("3/discover/tv?api_key=9b3efc88fed3cb0e25a0849788f05166&language=en-US")
    @Headers("Content-Type:application/json")
    fun getDataTV(): Call<ResponseTV>
}