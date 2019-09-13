package com.example.favoriteapp

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

public class Base {

    companion object {
        fun newInstance() = Base()
    }

    class dataBaseSQl : BaseColumns {
        companion object {
            val COLUMN_NAME_IMAGE = "image"
            val COLUMN_NAME_TITLE = "title"
            val COLUMN_NAME_ID = "id"
            val COLUMN_NAME_DESC = "description"
            val _ID = "_id"
        }
    }

    val AUTHORITY = "com.example.submission2"

    val URIMovie = Uri.Builder().scheme("content")
        .authority(AUTHORITY)
        .appendPath("GDKMovie")
        .build()

    fun getColumnInt(cursor: Cursor, columnName: String): Int {
        return cursor.getInt(cursor.getColumnIndex(columnName))
    }
    fun getColumnString(cursor: Cursor, columnName: String): String {
        return cursor.getString(cursor.getColumnIndex(columnName))
    }
}