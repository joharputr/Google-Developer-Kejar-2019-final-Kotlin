package com.example.submission2.SQLite

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.submission2.GDK
import com.example.submission2.TVshow.ResultsItemTv
import com.example.submission2.Widget.ImageBannerWidget
import com.example.submission2.movie.ResultsItemMovie


class DbHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 6
        private val DATABASE_NAME = "GDK.db"

        fun get(): DbHelper {
            return DbHelper(GDK.instance)
        }
    }

    fun getCursor(): Cursor {
        val columns = arrayOf<String>(
                Base.dataBaseSQl.COLUMN_NAME_ID,
                Base.dataBaseSQl.COLUMN_NAME_TITLE,
                Base.dataBaseSQl.COLUMN_NAME_DESC,
                Base.dataBaseSQl.COLUMN_NAME_IMAGE
        )
        val db = readableDatabase
        return db.query(
                "GDK",
                columns,
                null,
                null,
                null,
                null,
                Base.dataBaseSQl.COLUMN_NAME_ID + " DESC"
        )
    }


    fun getCursorMovie(): Cursor {
        val columns = arrayOf<String>(
                Base.dataBaseSQl.COLUMN_NAME_ID,
                Base.dataBaseSQl.COLUMN_NAME_TITLE,
                Base.dataBaseSQl.COLUMN_NAME_DESC,
                Base.dataBaseSQl.COLUMN_NAME_IMAGE
        )
        val db = readableDatabase
        return db.query(
                "GDKMovie",
                columns,
                null,
                null,
                null,
                null,
                Base.dataBaseSQl.COLUMN_NAME_ID + " DESC"
        )
    }

    fun addFavorite(users: ResultsItemTv?) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(Base.dataBaseSQl.COLUMN_NAME_ID, users?.id)
        values.put(Base.dataBaseSQl.COLUMN_NAME_TITLE, users?.name)
        values.put(Base.dataBaseSQl.COLUMN_NAME_DESC, users?.overview)
        values.put(Base.dataBaseSQl.COLUMN_NAME_IMAGE, users?.backdropPath)
        db.insert("GDK", null, values)
        db.close()
        Log.v("cekSQL ", " Record Inserted Sucessfully")
    }

    fun addFavoriteMovie(users: ResultsItemMovie?) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(Base.dataBaseSQl.COLUMN_NAME_ID, users?.id)
        values.put(Base.dataBaseSQl.COLUMN_NAME_TITLE, users?.title)
        values.put(Base.dataBaseSQl.COLUMN_NAME_DESC, users?.overview)
        values.put(Base.dataBaseSQl.COLUMN_NAME_IMAGE, users?.backdropPath)
        db.insert("GDKMovie", null, values)
        db.close()
        Log.v("cekSQL GDKMovie ", " Record Inserted Sucessfully GDKMovie")
        GDK.instance?.let { sendUpdateFavoriteList(it) }
    }

    fun removefromfav(usersId: String?) {
        val data = writableDatabase
        val where = Base.dataBaseSQl.COLUMN_NAME_ID + " = '" + usersId + "'"
        data.delete("GDK", where, null)
        val result = data.delete("GDK", where, null)

        Log.i(TAG, "removeGeofence requestId=$usersId, number of deleted rows=$result")
        Log.v("cekSQL ", " Record delete Sucessfully")
    }


    fun removefromfavMovie(usersId: String?) {
        val data = writableDatabase
        val where = Base.dataBaseSQl.COLUMN_NAME_ID + " = '" + usersId + "'"
        data.delete("GDKMovie", where, null)
        val result = data.delete("GDKMovie", where, null)

        Log.i(TAG, " GDKMovie removeGeofence requestId=$usersId, number of deleted rows=$result")
        Log.v("cekSQL GDKMovie", " Record delete Sucessfully GDKMovie")



        GDK.instance?.let { sendUpdateFavoriteList(it) }
    }


    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE GDK (id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,description TEXT,image TEXT)"
        val queryMovie = "CREATE TABLE GDKMovie (id  INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,description TEXT,image TEXT)"
        db.execSQL(query)
        db.execSQL(queryMovie)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val delete = "DROP TABLE IF EXISTS GDK"
        val deleteMovie = "DROP TABLE IF EXISTS GDKMovie"
        db.execSQL(delete)
        db.execSQL(deleteMovie)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun sendUpdateFavoriteList(context: Context) {
        val i = Intent(context, ImageBannerWidget::class.java)
        i.action = ImageBannerWidget.UPDATE_WIDGET
        context.sendBroadcast(i)
    }


}
