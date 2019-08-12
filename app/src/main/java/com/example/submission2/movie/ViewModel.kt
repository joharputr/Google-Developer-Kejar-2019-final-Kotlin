package com.example.submission2.movie

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class ViewModel: ViewModel(){
    private lateinit var movieRepository: Presenter

    fun getAllBlog(): MutableLiveData<List<ResultsItem>> {
        movieRepository = Presenter()
        return movieRepository.setData()
    }
}