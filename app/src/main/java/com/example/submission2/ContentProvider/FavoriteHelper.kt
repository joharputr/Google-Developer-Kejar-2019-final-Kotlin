package com.example.submission2.ContentProvider

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.submission2.SQLite.Base
import com.example.submission2.SQLite.DbHelper

class FavoriteHelper(private val context: Context) {
    private var dataBaseHelper: DbHelper? = null
    private var database: SQLiteDatabase? = null

    @Throws(SQLException::class)
    fun open(): FavoriteHelper {
        dataBaseHelper = DbHelper(context)
        database = dataBaseHelper!!.getWritableDatabase()
        return this
    }

    fun close() {
        dataBaseHelper!!.close()
    }


    fun queryByIdProvider(id: String): Cursor {
        return database!!.query(
            DATABASE_TABLE,
            null,
            Base.dataBaseSQl.COLUMN_NAME_ID + " = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun queryProvider(): Cursor {
        return database!!.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            Base.dataBaseSQl.COLUMN_NAME_ID + " DESC"
        )
    }

    fun insertProvider(values: ContentValues): Long {
        return database!!.insert(DATABASE_TABLE, null, values)
    }

    fun updateProvider(id: String, values: ContentValues): Int {
        return database!!.update(
            DATABASE_TABLE,
            values,
            Base.dataBaseSQl.COLUMN_NAME_ID + " = ?",
            arrayOf(id)
        )
    }

    fun deleteProvider(id: String): Int {
        return database!!.delete(
            DATABASE_TABLE,
            Base.dataBaseSQl.COLUMN_NAME_ID + " = ?",
            arrayOf(id)
        )
    }

    companion object {

        private val DATABASE_TABLE = "GDKMovie"
    }

}
