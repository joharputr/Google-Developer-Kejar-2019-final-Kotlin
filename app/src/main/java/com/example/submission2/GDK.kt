package com.example.submission2

import android.app.Application
import com.example.submission2.API.ApiService
import com.google.gson.Gson

class GDK : Application() {
    companion object {
        lateinit var gson: Gson
        lateinit var api: ApiService
        lateinit var instance: GDK
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        //api = NetworkConfig.getRetrofit().create(ApiService::class.java)
    }
}