package com.example.submission2.Favorite.MovieFav

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.submission2.SQLite.Base
import com.example.submission2.SQLite.DbHelper
import com.example.submission2.SQLite.dataFavMovie
import kotlinx.android.synthetic.main.fragment_moviesfav.*

class MovieFragmentFav : Fragment() {

    private var fav: MutableList<dataFavMovie> = mutableListOf()
    private lateinit var adapter: MovieAdapterFav
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dbHelper = DbHelper(context)
        dbHelper.getCursorMovie().use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(Base.dataBaseSQl.COLUMN_NAME_ID))
                val title = cursor.getString(cursor.getColumnIndex(Base.dataBaseSQl.COLUMN_NAME_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(Base.dataBaseSQl.COLUMN_NAME_DESC))
                val image = cursor.getString(cursor.getColumnIndex(Base.dataBaseSQl.COLUMN_NAME_IMAGE))
                val dataFav1 = dataFavMovie(id, title, description, image)
                Log.d(" itemTest = ", "" + dataFav1)
                fav.addAll(listOf(dataFav1))
            }
        }
        return inflater.inflate(com.example.submission2.R.layout.fragment_moviesfav, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_fav.layoutManager = LinearLayoutManager(context)
        rv_fav.setHasFixedSize(true)
        adapter = MovieAdapterFav(context, fav)
        rv_fav.adapter = adapter

    }

}