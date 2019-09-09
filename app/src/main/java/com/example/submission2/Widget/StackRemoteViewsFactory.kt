package com.example.submission2.Widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.submission2.R
import com.example.submission2.SQLite.Base
import com.example.submission2.TVshow.ResultsItemTv
import java.util.concurrent.ExecutionException


/**
 * Implementation of App Widget functionality.
 */

class StackRemoteViewsFactory(private val mContext: Context, intent: Intent) :
    RemoteViewsService.RemoteViewsFactory {
    private val mAppWidgetId: Int
    private var cursor: Cursor? = null
    private val DbHelper = com.example.submission2.SQLite.DbHelper

    private var list = ArrayList<String>()
    val helper = com.example.submission2.SQLite.DbHelper(mContext)
    val db = helper.getReadableDatabase()

    init {
        mAppWidgetId = intent.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )


    }


    override fun onCreate() {

    }

    override fun onDataSetChanged() {


        if (cursor != null) {
            cursor!!.close()
        }

        val identitiyToken = Binder.clearCallingIdentity()

        cursor = mContext.contentResolver.query(Base.newInstance().URIMovie, null, null, null, null)

        Binder.restoreCallingIdentity(identitiyToken)

    }

    override fun onDestroy() {
        if (cursor != null) cursor!!.close()
    }

    override fun getCount(): Int {
        return if (cursor == null)
            0
        else
            cursor!!.count
    }

    private fun getItem(position: Int): ResultsItemTv {
        check(cursor!!.moveToPosition(position)) { "Position Invalid" }
        return ResultsItemTv(cursor!!)
    }

    override fun getViewAt(position: Int): RemoteViews? {

        if (position == AdapterView.INVALID_POSITION ||
            cursor == null || !cursor!!.moveToPosition(position)
        ) {
            return null
        }

        val detailMovie = getItem(position)
        val rv = RemoteViews(mContext.packageName, R.layout.item_widget)

        var bmp: Bitmap? = null
        val movie_title = detailMovie.name
        try {

            bmp = Glide.with(mContext)
                .asBitmap()
                .load("https://image.tmdb.org/t/p/w185" + detailMovie.backdropPath)
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()

        } catch (e: InterruptedException) {
            Log.d("Widget Load Error", "error")
        } catch (e: ExecutionException) {
            Log.d("Widget Load Error", "error")
        }

        rv.setImageViewBitmap(R.id.imageView, bmp)

        val extras = Bundle()
        extras.putString(ImageBannerWidget.EXTRA_ITEM, movie_title/* + "\n" + date*/)

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)

        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return if (cursor!!.moveToPosition(position)) {
            cursor!!.getLong(0)
        } else
            position.toLong()
    }


    override fun hasStableIds(): Boolean {
        return true
    }


}