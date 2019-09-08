package com.example.submission2.SQLite

import android.database.Cursor
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class dataFavMovie(

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("overview")
    var overview: String? = null,

    @field:SerializedName("backdrop_path")
    var backdropPath: String? = null

    /*@field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @field:SerializedName("original_title")
    val originalTitle: String? = null,

    @field:SerializedName("video")
    val video: Boolean? = null,


    @field:SerializedName("genre_ids")
    val genreIds: List<Int?>? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,



    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,



    @field:SerializedName("adult")
    val adult: Boolean? = null,

    @field:SerializedName("vote_count")
    val voteCount: Int? = null*/


) : Parcelable {
    constructor(cursor: Cursor) : this() {
        this.id = Base.newInstance().getColumnInt(cursor, Base.dataBaseSQl.COLUMN_NAME_ID)
        this.title = Base.newInstance().getColumnString(cursor, Base.dataBaseSQl.COLUMN_NAME_TITLE)
        this.overview =
            Base.newInstance().getColumnString(cursor, Base.dataBaseSQl.COLUMN_NAME_DESC)
        this.backdropPath =
            Base.newInstance().getColumnString(cursor, Base.dataBaseSQl.COLUMN_NAME_IMAGE)
    }



}

