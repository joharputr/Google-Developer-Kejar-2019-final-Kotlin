package com.example.submission2.SQLite

import android.content.ContentValues
import android.content.Context
import android.content.RestrictionsManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.submission2.GDK
import com.example.submission2.TVshow.ResultsItemTv

class DbHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 5
        private val DATABASE_NAME = "GDK3.db"

        fun get(): DbHelper {
            return DbHelper(GDK.instance)
        }

    }
    fun populatePerson(users: ResultsItemTv) {
        val db = writableDatabase
        var values = ContentValues()
        values.put(Base.dataBaseSQl.COLUMN_NAME_TITLE, users.name)
        values.put(Base.dataBaseSQl.COLUMN_NAME_DESC, users.overview)
        values.put(Base.dataBaseSQl.COLUMN_NAME_IMAGE, users.backdropPath)
        db.insert("GDK", null, values)
        db.close()
        Log.v("cekSQL ", " Record Inserted Sucessfully")
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE GDK (title TEXT,description TEXT,image TEXT)"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS GDK")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}
