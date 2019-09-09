package com.example.submission2.TVshow


import android.database.Cursor
import android.os.Parcelable
import com.example.submission2.SQLite.Base
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultsItemTv(


        @field:SerializedName("id")
        var id: Int? = null,

        @field:SerializedName("overview")
        var overview: String? = null,

        @field:SerializedName("backdrop_path")
        var backdropPath: String? = null,

        @field:SerializedName("name")
        var name: String? = null

     /*   @field:SerializedName("original_name")
        val originalName: String? = null,


        @field:SerializedName("first_air_date")
        val firstAirDate: String? = null,

        @field:SerializedName("original_language")
        val originalLanguage: String? = null,

        @field:SerializedName("genre_ids")
        val genreIds: List<Int?>? = null,

        @field:SerializedName("poster_path")
        val posterPath: String? = null,

        @field:SerializedName("origin_country")
        val originCountry: List<String?>? = null,

        @field:SerializedName("popularity")
        val popularity: Double? = null,

        @field:SerializedName("vote_average")
        val voteAverage: Double? = null,




        @field:SerializedName("vote_count")
        val voteCount: Int? = null*/
) : Parcelable{
        constructor(cursor: Cursor) : this() {
                this.id = Base.newInstance().getColumnInt(cursor, Base.dataBaseSQl.COLUMN_NAME_ID)
                this.name = Base.newInstance().getColumnString(cursor, Base.dataBaseSQl.COLUMN_NAME_TITLE)
                this.overview =
                        Base.newInstance().getColumnString(cursor, Base.dataBaseSQl.COLUMN_NAME_DESC)
                this.backdropPath =
                        Base.newInstance().getColumnString(cursor, Base.dataBaseSQl.COLUMN_NAME_IMAGE)
        }

}