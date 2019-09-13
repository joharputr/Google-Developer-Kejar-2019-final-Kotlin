package com.example.favoriteapp

import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var list: Cursor? = null
    private var array = ArrayList<ResultsItemMovie>()

   lateinit var adapter: MovieAdapter
    var helper = MappingHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.visibility = View.VISIBLE
        LoadFavouriteAsync().execute()




        adapter = MovieAdapter(array, this@MainActivity)

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
            adapter = MovieAdapter(array, this@MainActivity)
            Log.d("array=",array.toString())
            movieRecyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
            movieRecyclerview.adapter = MovieAdapter(array, this@MainActivity)
            if (list!!.count == 0) {
                Toast.makeText(this@MainActivity, "data kososng", Toast.LENGTH_SHORT).show()
            }

        }
    }


}
