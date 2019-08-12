package com.example.submission2.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.submission2.GDK

class DbHelper private constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {

        private val DATABASE_VERSION = 5
        private val DATABASE_NAME = "GDK.db"
        private val SQL_CREATE_ENTRIES = "CREATE TABLE " + Base.dataBaseSQl.TABLE_NAME +
                "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, keys TEXT,lat TEXT,lng TEXT,expires TEXT)"


        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Base.dataBaseSQl.TABLE_NAME

        fun get(): DbHelper {
            return DbHelper(GDK.instance)
        }

        fun saveToDb( title: String, description: String) {
            val helper = get()

            //save to db
            val values = ContentValues()
           // values.put(Base.dataBaseSQl.COLUMN_NAME_KEY, key)
            values.put(Base.dataBaseSQl.COLUMN_NAME_TITLE, title + "")
            values.put(Base.dataBaseSQl.COLUMN_NAME_DESC, description + "")
            helper.writableDatabase.insert(Base.dataBaseSQl.TABLE_NAME, null, values)
            Log.i("", "Row inserted id=$helper, ContentValues=$values")

        }
    }

}