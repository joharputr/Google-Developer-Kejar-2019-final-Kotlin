package com.example.submission2.TVshow

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.submission2.Detail
import com.example.submission2.TVshow.SearchTV.SearchTV
import kotlinx.android.synthetic.main.fragment_tvshow.*


class TVshowFragment : Fragment() {

    val data = ArrayList<ResultsItemTv>()
    var viewModelTv = PresenterTV()

    val adaptertv = tvAdapter(data, this::onCLick)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.example.submission2.R.layout.fragment_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelTv = ViewModelProviders.of(this).get(PresenterTV::class.java)
        viewModelTv.setData().observe(this, object : Observer<List<ResultsItemTv>> {
            override fun onChanged(dataMovie: List<ResultsItemTv>?) {
                if (dataMovie != null) {
                    adaptertv.setData(dataMovie as java.util.ArrayList<ResultsItemTv>)
                    //        showLoading(false)
                }
            }
        })

        viewModelTv.setStatus().observe(this, Observer {
            if (it ?: true) {
                pBarTV.visibility = View.VISIBLE
            } else {
                pBarTV.visibility = View.GONE
            }
        })

        tvrec.run {
            // layout baris kebawah
            layoutManager = LinearLayoutManager(context)
            adapter = adaptertv

        }

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val componentName = ComponentName(context!!, SearchTV::class.java)

        searchTV.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchTV.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("querysub =", query)

                return false
            }

        })
    }


    private fun onCLick(tvData: ResultsItemTv) {

        val intent = Intent(activity, Detail::class.java)
        intent.putExtra("tv", tvData)
        startActivity(intent)

    }

}
