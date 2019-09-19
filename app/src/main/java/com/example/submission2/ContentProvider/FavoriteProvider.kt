package com.example.submission2.ContentProvider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.submission2.SQLite.Base

class FavouriteProvider : ContentProvider() {

    private var favouriteHelper: FavoriteHelper? = null

    companion object {

        private val MOVIE = 1
        private val MOVIE_ID = 2

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {

            sUriMatcher.addURI(Base.newInstance().AUTHORITY, "GDKMovie", MOVIE)

            sUriMatcher.addURI(
                Base.newInstance().AUTHORITY,
                "GDKMovie" + "/#",
                MOVIE_ID
            )
        }
    }

    override fun onCreate(): Boolean {
        favouriteHelper = FavoriteHelper(context!!)
        favouriteHelper!!.open()
        return true
    }

    override fun query(
        uri: Uri,
        strings: Array<String>?,
        s: String?,
        strings1: Array<String>?,
        s1: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            MOVIE -> cursor = favouriteHelper!!.queryProvider()
            MOVIE_ID -> cursor = favouriteHelper!!.queryByIdProvider(uri.lastPathSegment!!)
            else -> cursor = null
        }

        if (cursor != null) {
            cursor.setNotificationUri(context!!.contentResolver, uri)
        }

        return cursor
    }


    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {

        val added: Long

        when (sUriMatcher.match(uri)) {
            MOVIE -> added = favouriteHelper!!.insertProvider(contentValues!!)
            else -> added = 0
        }

        if (added > 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }
        return Uri.parse("" + Base.newInstance().URIMovie + "/" + added)
    }


    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        s: String?,
        strings: Array<String>?
    ): Int {
        val updated: Int
        when (sUriMatcher.match(uri)) {
            MOVIE_ID -> updated =
                favouriteHelper!!.updateProvider(uri.lastPathSegment, contentValues!!)
            else -> updated = 0
        }

        if (updated > 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }
        return updated
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        val deleted: Int
        when (sUriMatcher.match(uri)) {
            MOVIE_ID -> deleted = favouriteHelper!!.deleteProvider(uri.lastPathSegment)
            else -> deleted = 0
        }

        if (deleted > 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }

        return deleted
    }


}