package com.example.submission2.Favorite.TVFav

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.submission2.SQLite.Base
import com.example.submission2.SQLite.DbHelper

import com.example.submission2.SQLite.dataFavTV
import kotlinx.android.synthetic.main.fragment_moviesfav.*


class TvFragmentFav : Fragment() {

    companion object {
        fun newInstance() = TvFragmentFav()
    }

    private var fav: MutableList<dataFavTV> = mutableListOf()
    private lateinit var adapter: TvAdapterFav
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dbHelper = DbHelper(context)
        dbHelper.getCursor().use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(Base.dataBaseSQl.COLUMN_NAME_ID))
                val name = cursor.getString(cursor.getColumnIndex(Base.dataBaseSQl.COLUMN_NAME_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(Base.dataBaseSQl.COLUMN_NAME_DESC))
                val image = cursor.getString(cursor.getColumnIndex(Base.dataBaseSQl.COLUMN_NAME_IMAGE))
                val dataFav1 = dataFavTV(id, name, description, image)
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
        adapter = TvAdapterFav(context, fav)
        rv_fav.adapter = adapter

    }

}