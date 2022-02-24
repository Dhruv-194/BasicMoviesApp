package com.dhruv.moviesapp.data.vo


import com.google.gson.annotations.SerializedName

data class TRMovieResponse(
    val page: Int,
    @SerializedName("results")
    val TRMovieResults: List<TRMovieResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)