package com.example.submission2.movie.SearchMovie

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.submission2.R
import com.example.submission2.SQLite.DbHelper
import com.example.submission2.TVshow.ResultsItemTv
import com.example.submission2.movie.ResultsItemMovie
import kotlinx.android.synthetic.main.movielist.view.*
import java.util.ArrayList

class SearchMovieAdapter(
    val itemList: ArrayList<ResultsItemMovie>,
    private val onClick: (ResultsItemMovie) -> Unit
) : RecyclerView.Adapter<SearchMovieAdapter.movieHolder>() {

    fun getMovie(): ArrayList<ResultsItemMovie> {
        return this.itemList
    }

    class movieHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        fun bind(item: ResultsItemMovie) = itemView.apply {
            tv_item_name.text = item.title
            tv_item_deskription.text = item.overview
            Glide.with(img_item_photo.context).load("https://image.tmdb.org/t/p/w185" + item.backdropPath)
                .into(img_item_photo)
        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): movieHolder {
        val inflater = LayoutInflater.from(p0.context)
        val view = inflater.inflate(R.layout.movielist, p0, false)
        return movieHolder(view)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(p0: movieHolder, p1: Int) {
        val item = itemList[p1]
        p0.bind(item)
        p0.itemView.setOnClickListener {
            onClick(item)
        }
    }
}

