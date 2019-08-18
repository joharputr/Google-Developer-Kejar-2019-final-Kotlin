package com.example.submission2.TVshow

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.submission2.R
import com.example.submission2.SQLite.DbHelper
import kotlinx.android.synthetic.main.tvlist.view.*
import java.util.*


class tvAdapter(
    val itemList: ArrayList<ResultsItemTv>,
    private val onClick: (ResultsItemTv) -> Unit
) : RecyclerView.Adapter<tvAdapter.movieHolder>() {

    fun setData(items: ArrayList<ResultsItemTv>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    class movieHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //  private var isFavorite: Boolean = true
        fun bind(item: ResultsItemTv) = itemView.apply {
            tv_item_nameTv.text = item.name
            tv_item_deskriptionTv.text = item.overview
            Glide.with(img_item_photoTV.context).load("https://image.tmdb.org/t/p/w185" + item.backdropPath)
                .into(img_item_photoTV)


            val helper = DbHelper(context)

            btnFav.setOnClickListener {
                helper.removefromfav(item.id.toString())
                Toast.makeText(context, "" + item.name + " delete from Favorite TV list", Toast.LENGTH_SHORT).show()
                btnTambahFav.visibility = View.VISIBLE
                btnFav.visibility = View.GONE

            }


            btnTambahFav.setOnClickListener {
                Toast.makeText(context, "" + item.name + " has been added to Favorite TV list", Toast.LENGTH_SHORT)
                    .show()
                helper.addFavorite(item)
                Log.d("test", "add = " + item)
                btnTambahFav.visibility = View.GONE
                btnFav.visibility = View.VISIBLE

            }
        }


    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): movieHolder {
        val inflater = LayoutInflater.from(p0.context)
        val view = inflater.inflate(R.layout.tvlist, p0, false)
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