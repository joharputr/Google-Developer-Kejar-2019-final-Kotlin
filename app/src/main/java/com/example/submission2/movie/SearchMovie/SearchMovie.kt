package com.example.submission2.movie.SearchMovie

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.submission2.API.NetworkConfig
import com.example.submission2.Detail
import com.example.submission2.movie.ResultsItemMovie
import kotlinx.android.synthetic.main.searchmovielayout.*
import kotlinx.android.synthetic.main.searchtvlayout.pBarTVsearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchMovie : AppCompatActivity() {
    var data = arrayListOf<ResultsItemMovie>()
    private var search: String? = null
    private val LANGUAGE = "en-US"
    private var list: java.util.ArrayList<ResultsItemMovie>? =
        java.util.ArrayList()

    var adapterMovie = SearchMovieAdapter(list!!, this::onCLick)

    private fun onCLick(tvData: ResultsItemMovie) {
        val intent = Intent(applicationContext, Detail::class.java)
        intent.putExtra("DATA", tvData)
        startActivity(intent)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.submission2.R.layout.searchmovielayout)
        setSupportActionBar(toolbarMovie)
        handleIntent(intent)
        val actionBar = supportActionBar
        actionBar!!.title = "GDK DICODING"
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setLogo(com.example.submission2.R.mipmap.ic_launcher)
        actionBar.setDisplayUseLogoEnabled(true)

        if (supportActionBar != null) supportActionBar!!.title = search

        if (savedInstanceState != null) {
            list = savedInstanceState.getParcelableArrayList("searchMovie")
            prepareView()
        } else {
         //   list = ArrayList()
            getSearchResult()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("datagetmovie =", adapterMovie.getMovie().toString());
        outState.putParcelableArrayList("searchMovie", ArrayList(adapterMovie.getMovie()))

    }


    private fun prepareView() {
        adapterMovie = SearchMovieAdapter(list!!, this::onCLick)
        searchMovieRecycler.run {
            Log.d("datasaat SItidak null =", adapterMovie.getMovie().toString());
            layoutManager = LinearLayoutManager(context)
            adapter = adapterMovie
        }
    }

    override fun onNewIntent(intent: Intent?) {
        handleIntent(intent!!)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            search = intent.getStringExtra(SearchManager.QUERY)
        }
    }

    private fun getSearchResult() {
        pBarTVsearch.visibility = View.VISIBLE
        val apiService = NetworkConfig.getApiService()
        val call = apiService.getSearchMovie2("9b3efc88fed3cb0e25a0849788f05166", LANGUAGE, search)

        call.enqueue(object : Callback<com.example.submission2.movie.Response> {
            override fun onResponse(
                call: Call<com.example.submission2.movie.Response>,
                response: Response<com.example.submission2.movie.Response>
            ) {
                pBarTVsearch.visibility = View.GONE
                data = response.body()?.results as ArrayList<ResultsItemMovie>
                if (response.body() != null) {
                    list?.addAll(data)
                    Log.d("mutabel= ", list.toString())

                //    adapterMovie = SearchMovieAdapter(list!!, this)
                    searchMovieRecycler.run {
                        layoutManager = LinearLayoutManager(context)
                        adapter = adapterMovie
                    }
                }
                if (list?.size == 0) {
                    Toast.makeText(applicationContext, "data kosong", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<com.example.submission2.movie.Response>,
                t: Throwable
            ) {
                pBarTVsearch.visibility = View.GONE
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }

}
