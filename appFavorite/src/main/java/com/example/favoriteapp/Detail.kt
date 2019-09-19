package com.example.favoriteapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class Detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val data: ResultsItemMovie? = intent.getParcelableExtra("DATA")


        if (intent.hasExtra("DATA")) {
            nama.text = data?.title
            deskription.text = data?.overview
            Glide.with(images.context).load("https://image.tmdb.org/t/p/w185" + data?.backdropPath)
                .into(images)

        }
    }
}