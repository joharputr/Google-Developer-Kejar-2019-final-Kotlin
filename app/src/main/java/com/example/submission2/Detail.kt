package com.example.submission2

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.submission2.TVshow.ResultsItemTv
import com.example.submission2.movie.ResultsItemMovie
import kotlinx.android.synthetic.main.activity_detail.*

class Detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val data: ResultsItemMovie? = intent.getParcelableExtra("DATA")
        val tv: ResultsItemTv? = intent.getParcelableExtra("tv")

        if (intent.hasExtra("DATA")) {
            nama.text = data?.title
            deskription.text = data?.overview
            Glide.with(images.context).load("https://image.tmdb.org/t/p/w185" + data?.backdropPath)
                    .into(images)
        } else if (intent.hasExtra("tv")) {
            nama.text = tv?.name
            deskription.text = tv?.overview
            Glide.with(images.context).load("https://image.tmdb.org/t/p/w185" + tv?.backdropPath)
                    .into(images)

        }

        setSupportActionBar(toolbar1)

        val actionBar = supportActionBar

        actionBar!!.title = "SUB 2"
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setLogo(R.mipmap.ic_launcher)
        actionBar.setDisplayUseLogoEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_change_settings -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}
