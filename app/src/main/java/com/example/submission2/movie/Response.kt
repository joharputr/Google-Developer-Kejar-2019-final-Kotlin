package com.example.submission2.movie


import com.google.gson.annotations.SerializedName


data class Response(
    @field:SerializedName("page")
	val page: Int? = null,

    @field:SerializedName("total_pages")
	val totalPages: Int? = null,

    @field:SerializedName("results")
	val results: ArrayList<ResultsItemMovie>? = null,

    @field:SerializedName("total_results")
	val totalResults: Int? = null
){
	fun dataResult(): ArrayList<ResultsItemMovie>? {
		return results
	}

}