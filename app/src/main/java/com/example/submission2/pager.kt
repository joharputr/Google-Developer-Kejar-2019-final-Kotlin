package com.example.submission2

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.submission2.TVshow.TVshowFragment
import com.example.submission2.movie.movieFragment
import kotlin.coroutines.coroutineContext


class pager(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
    var title: String? = null
    private val pages = listOf(
        movieFragment(),
        TVshowFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when (position) {
            0 -> ""+ context.resources.getString(R.string.movie)
            else -> ""+context.resources.getString(R.string.pertunjukantv)
        }
    }
}