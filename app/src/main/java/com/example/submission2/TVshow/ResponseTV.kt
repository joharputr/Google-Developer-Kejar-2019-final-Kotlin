package com.example.submission2.TVshow


import com.google.gson.annotations.SerializedName

data class ResponseTV(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItemTv?>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)