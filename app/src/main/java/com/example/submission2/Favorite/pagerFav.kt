package com.example.submission2.Favorite

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.submission2.Favorite.MovieFav.MovieFragmentFav
import com.example.submission2.Favorite.TVFav.TvFragmentFav
import com.example.submission2.R

class pagerFav(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
    var title: String? = null
    private val pages = listOf(

        TvFragmentFav(),
        MovieFragmentFav()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when (position) {
            0 -> "" + context.resources.getString(R.string.pertunjukantv)
            else -> "" + context.resources.getString(R.string.movie)
        }
    }
}