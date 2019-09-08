package com.example.submission2.TVshow.SearchTV

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.submission2.API.NetworkConfig
import com.example.submission2.Detail
import com.example.submission2.R
import com.example.submission2.TVshow.ResponseTV
import com.example.submission2.TVshow.ResultsItemTv
import kotlinx.android.synthetic.main.searchtvlayout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchTV : AppCompatActivity() {
    var data = arrayListOf<ResultsItemTv>()
    private var search: String? = null
    private val LANGUAGE = "en-US"
  //  private var mutableListTv: MutableList<ResultsItemTv> = ArrayList();
  private var mutableListTv: java.util.ArrayList<ResultsItemTv>? = java.util.ArrayList<ResultsItemTv>()
    var adaptertv = SearchTvAdapter(mutableListTv, this::onCLick)

    private fun onCLick(tvData: ResultsItemTv) {
        val intent = Intent(applicationContext, Detail::class.java)
        intent.putExtra("tv", tvData)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchtvlayout)
        setSupportActionBar(toolbar1)
        handleIntent(intent)
        val actionBar = supportActionBar

        actionBar!!.title = "GDK DICODING"

        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setLogo(R.mipmap.ic_launcher)
        actionBar.setDisplayUseLogoEnabled(true)

        if (supportActionBar != null) supportActionBar!!.title = search
        if (savedInstanceState != null) {
            mutableListTv = savedInstanceState.getParcelableArrayList("tv")
            prepareView()
        } else {
            getSearchResult()
        }

    }

    public override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("tv", ArrayList(adaptertv.gwtTV()))
        super.onSaveInstanceState(outState)
    }

    private fun prepareView() {
        adaptertv = SearchTvAdapter(mutableListTv, this::onCLick)
        searchTVRecycler.run {
            layoutManager = LinearLayoutManager(context)
            adapter = adaptertv
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
        val call = apiService.getSearchTV2("9b3efc88fed3cb0e25a0849788f05166", LANGUAGE, search)

        call.enqueue(object : Callback<ResponseTV> {
            override fun onResponse(call: Call<ResponseTV>, response: Response<ResponseTV>) {
                pBarTVsearch.visibility = View.GONE
                data = response.body()?.results as ArrayList<ResultsItemTv>
                if (response.body() != null) {
                    mutableListTv?.addAll(data)
                    searchTVRecycler.run {
                        layoutManager = LinearLayoutManager(context)
                        adapter = adaptertv
                    }
                }
                if (mutableListTv?.size == 0) {
                    Toast.makeText(applicationContext, "data kosong", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseTV>, t: Throwable) {
                pBarTVsearch.visibility = View.GONE
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }

}

