package com.example.favoriteapp

import android.database.Cursor
import android.provider.ContactsContract.CommonDataKinds.Note
import com.example.favoriteapp.Base.dataBaseSQl.Companion.COLUMN_NAME_ID
import com.example.favoriteapp.Base.dataBaseSQl.Companion._ID


object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor): ArrayList<ResultsItemMovie> {
        val notesList = ArrayList<ResultsItemMovie>()
        while (notesCursor.moveToNext()) {
            val id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(COLUMN_NAME_ID))
            val title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(Base.dataBaseSQl.COLUMN_NAME_TITLE))
            val description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(Base.dataBaseSQl.COLUMN_NAME_DESC))
            val image = notesCursor.getString(notesCursor.getColumnIndexOrThrow(Base.dataBaseSQl.COLUMN_NAME_IMAGE))
            notesList.add(ResultsItemMovie(id, title, description, image))
        }
        return notesList
    }
}