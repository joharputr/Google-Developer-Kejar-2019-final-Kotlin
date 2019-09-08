package com.example.submission2.API

import com.example.submission2.TVshow.ResponseTV
import com.example.submission2.movie.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie?api_key=9b3efc88fed3cb0e25a0849788f05166&language=en-US")
    @Headers("Content-Type:application/json")
    fun getDataMovie(): Call<Response>

    @GET("discover/tv?api_key=9b3efc88fed3cb0e25a0849788f05166&language=en-US")
    @Headers("Content-Type:application/json")
    fun getDataTV(): Call<ResponseTV>


    @GET("search/tv")
     fun getSearchTV2(
        @Query("api_key") apiKey: String, @Query("language") language: String, @Query(
            "query"
        ) querySearch: String?
    ): Call<ResponseTV>

    @GET("search/movie")
    fun getSearchMovie2(
        @Query("api_key") apiKey: String, @Query("language") language: String, @Query(
            "query"
        ) querySearch: String?
    ): Call<Response>

}