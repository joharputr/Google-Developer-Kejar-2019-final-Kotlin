package com.example.submission2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.submission2.SQLite.DbHelper
import com.example.submission2.TVshow.ResultsItemTv
import com.example.submission2.Widget.ImageBannerWidget
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

            val helper = DbHelper(this)
            val db = helper.getReadableDatabase()
            val cMov = db.rawQuery(
                "SELECT id,title,description,image  FROM GDKMovie WHERE id =  " + data?.id.toString(),
                null
            )

            if (cMov.moveToFirst()) {
                btnTambahFav.visibility = View.GONE
                btnFav.visibility = View.VISIBLE
                btnFav.setOnClickListener {
                    helper.removefromfavMovie(data?.id.toString())
                    Toast.makeText(
                        this,
                        "" + data?.title.toString() + " removed",
                        Toast.LENGTH_SHORT
                    ).show()
                    btnTambahFav.visibility = View.VISIBLE
                    btnFav.visibility = View.GONE
                }
                btnTambahFav.setOnClickListener {
                    helper.addFavoriteMovie(data)
                    Toast.makeText(this, "" + data?.title + "added to favorite", Toast.LENGTH_SHORT)
                        .show()
                    btnTambahFav.visibility = View.GONE
                    btnFav.visibility = View.VISIBLE
                }

            } else {
                btnTambahFav.visibility = View.VISIBLE
                btnFav.visibility = View.GONE
                btnTambahFav.setOnClickListener {
                    helper.addFavoriteMovie(data)
                    Toast.makeText(this, "" + data?.title + "added to favorite", Toast.LENGTH_SHORT)
                        .show()
                    btnTambahFav.visibility = View.GONE
                    btnFav.visibility = View.VISIBLE
                }

                btnFav.setOnClickListener {
                    helper.removefromfavMovie(data?.id.toString())
                    Toast.makeText(
                        this,
                        "" + data?.title.toString() + "removed",
                        Toast.LENGTH_SHORT
                    ).show()
                    btnTambahFav.visibility = View.VISIBLE
                    btnFav.visibility = View.GONE
                }
            }

            cMov.close()
            db.close()
        } else if (intent.hasExtra("tv")) {
            nama.text = tv?.name
            deskription.text = tv?.overview
            Glide.with(images.context).load("https://image.tmdb.org/t/p/w185" + tv?.backdropPath)
                .into(images)

            val helper = DbHelper(this)
            val db = helper.getReadableDatabase()
            val c = db.rawQuery(
                "SELECT id,title,description,image  FROM GDK WHERE id =  " + tv?.id.toString(),
                null
            )

            if (c.moveToFirst()) {
                btnTambahFav.visibility = View.GONE
                btnFav.visibility = View.VISIBLE
                btnFav.setOnClickListener {
                    helper.removefromfav(tv?.id.toString())
                    Toast.makeText(this, "" + tv?.name.toString() + " removed", Toast.LENGTH_SHORT)
                        .show()
                    btnTambahFav.visibility = View.VISIBLE
                    btnFav.visibility = View.GONE
                }
                btnTambahFav.setOnClickListener {
                    helper.addFavorite(tv)
                    Toast.makeText(this, "" + tv?.name + "added to favorite", Toast.LENGTH_SHORT)
                        .show()
                    btnTambahFav.visibility = View.GONE
                    btnFav.visibility = View.VISIBLE
                }


            } else {
                btnTambahFav.visibility = View.VISIBLE
                btnFav.visibility = View.GONE
                btnTambahFav.setOnClickListener {
                    helper.addFavorite(tv)
                    Toast.makeText(this, "" + tv?.name + "added to favorite", Toast.LENGTH_SHORT)
                        .show()
                    btnTambahFav.visibility = View.GONE
                    btnFav.visibility = View.VISIBLE
                    sendUpdateFavoriteList(this)
                }
                btnFav.setOnClickListener {
                    helper.removefromfav(tv?.id.toString())
                    Toast.makeText(this, "" + tv?.name.toString() + " removed", Toast.LENGTH_SHORT)
                        .show()
                    btnTambahFav.visibility = View.VISIBLE
                    btnFav.visibility = View.GONE
                    sendUpdateFavoriteList(this)
                }
            }


            c.close()
            db.close()
        }




        setSupportActionBar(toolbar1)

        val actionBar = supportActionBar

        actionBar!!.title = "SUB 2"
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setLogo(R.mipmap.ic_launcher)
        actionBar.setDisplayUseLogoEnabled(true)
    }


    fun sendUpdateFavoriteList(context: Context) {
        val i = Intent(context, ImageBannerWidget::class.java)
        i.action = ImageBannerWidget.UPDATE_WIDGET
        context.sendBroadcast(i)
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
