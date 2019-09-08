package com.example.submission2.Favorite.TVFav

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.submission2.R
import com.example.submission2.SQLite.dataFavMovie
import com.example.submission2.SQLite.dataFavTV
import kotlinx.android.synthetic.main.layoutfav.view.*
import kotlinx.android.synthetic.main.movielist.view.*

class TvAdapterFav(val ctx: Context?,
                   private val onClick: (dataFavTV) -> Unit,
                   private val mList: List<dataFavTV>):
    RecyclerView.Adapter<TvAdapterFav.FavHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavHolder {
        return FavHolder(
            LayoutInflater.from(ctx).inflate(
                R.layout.layoutfav,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: FavHolder, position: Int) {
        val item = mList[position]
        holder.bind(mList[position])
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    class FavHolder(v: View): RecyclerView.ViewHolder(v) {

        fun bind(item: dataFavTV) = itemView.apply {
            tv_item_nameFav.text = item.name
            tv_item_deskriptionFav.text = item.overview
            Glide.with(img_item_photoFav.context).load("https://image.tmdb.org/t/p/w185" + item.backdropPath)
                .into(img_item_photoFav)
        }

    }


}