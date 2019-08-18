package com.example.submission2.movie

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class ViewModel: ViewModel(){
    private lateinit var movieRepository: Presenter

    fun getAllBlog(): MutableLiveData<List<ResultsItemMovie>> {
        movieRepository = Presenter()
        return movieRepository.setData()
    }
}