package com.example.submission2.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.submission2.Detail
import kotlinx.android.synthetic.main.fragment_movies.*


class movieFragment : Fragment() {

    companion object {
        fun newInstance() = movieFragment()
    }

    lateinit var mainViewModel: Presenter

    private val movieData = ArrayList<ResultsItem>()
    private val adapterMovie = movieAdapter(movieData, this::onClick)


    private fun onClick(data: ResultsItem) {
        val intent = Intent(activity, Detail::class.java)
        intent.putExtra("DATA", data)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.example.submission2.R.layout.fragment_movies, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapterMovie.notifyDataSetChanged()

        mainViewModel = ViewModelProviders.of(this).get(Presenter::class.java)
        movieRecyclerview.run {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterMovie
        }

        mainViewModel.setStatus().observe(this, Observer {status ->
            if (status ?: true){
                pBar.visibility = View.VISIBLE
            }
            else{
                pBar.visibility = View.GONE
            }

        })

        mainViewModel.setData().observe(this, object : Observer<List<ResultsItem>> {
            override fun onChanged(dataMovie: List<ResultsItem>?) {
                if (dataMovie != null) {
                    adapterMovie.setData(dataMovie as java.util.ArrayList<ResultsItem>)
                    //        showLoading(false)
                }
            }

        })

    }
}