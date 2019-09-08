package com.example.submission2.TVshow.SearchTV

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.submission2.R
import com.example.submission2.TVshow.ResultsItemTv
import kotlinx.android.synthetic.main.tvlist.view.*

class SearchTvAdapter(
    val itemList: ArrayList<ResultsItemTv>?,
    private val onClick: (ResultsItemTv) -> Unit
) : RecyclerView.Adapter<SearchTvAdapter.tvHolder>() {


    fun setData(items: ArrayList<ResultsItemTv>) {
        itemList?.clear()
        itemList?.addAll(items)
        notifyDataSetChanged()
    }

    fun gwtTV(): ArrayList<ResultsItemTv> {
        return this.itemList!!
    }

    class tvHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //  private var isFavorite: Boolean = true
        fun bind(item: ResultsItemTv?) = itemView.apply {
            tv_item_nameTv.text = item?.name
            tv_item_deskriptionTv.text = item?.overview
            Glide.with(img_item_photoTV.context)
                .load("https://image.tmdb.org/t/p/w185" + item?.backdropPath)
                .into(img_item_photoTV)

        }
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): tvHolder {
        val inflater = LayoutInflater.from(p0.context)
        val view = inflater.inflate(R.layout.tvlist, p0, false)

        return tvHolder(view)

    }

    override fun getItemCount(): Int {
        return itemList?.size!!
    }

    override fun onBindViewHolder(p0: tvHolder, p1: Int) {
        val item = itemList!!.get(p1)
        p0.bind(item)
        p0.itemView.setOnClickListener {
            onClick(item)
        }

    }



}