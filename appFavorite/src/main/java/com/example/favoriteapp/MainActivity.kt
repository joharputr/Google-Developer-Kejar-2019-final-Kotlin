package com.example.favoriteapp

import android.content.Intent
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), helper {


    override fun Data(resultsItemMovie: ResultsItemMovie) {

    }

    private var list: Cursor? = null
    private var list2: Cursor? = null

    private var array: java.util.ArrayList<ResultsItemMovie>? =
        java.util.ArrayList()

    var adapter = MovieAdapter(array!!, this::onClick)

    private fun onClick(itemMovie: ResultsItemMovie) {
        val intent = Intent(applicationContext, Detail::class.java)
        intent.putExtra("DATA", itemMovie)
        startActivity(intent)
    }

    var helper = MappingHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.visibility = View.VISIBLE
        LoadFavouriteAsync().execute()
        Log.d("arrayList=", array.toString())
        adapter = MovieAdapter(array!!, this::onClick)
    }


    override fun onResume() {
        super.onResume()
        LoadFavouriteAsync().execute()
    }


    private inner class LoadFavouriteAsync : AsyncTask<Void, Void, Cursor>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar!!.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg voids: Void): Cursor? {
            return contentResolver.query(Base.newInstance().URIMovie, null, null, null, null)
        }

        override fun onPostExecute(items: Cursor) {
            super.onPostExecute(items)
            progressBar!!.visibility = View.GONE

            list = items

            array = helper.mapCursorToArrayList(list!!)
            adapter.setListFavourite(array!!)

            Log.d("array=", array.toString())
            movieRecyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
            movieRecyclerview.adapter = adapter


            if (list!!.count == 0) {
                Toast.makeText(this@MainActivity, "data kososng", Toast.LENGTH_SHORT).show()
            }

        }
    }


}
