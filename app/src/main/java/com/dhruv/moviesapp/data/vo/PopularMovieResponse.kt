package com.dhruv.moviesapp.data.vo


import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(
    val page: Int,
    @SerializedName("results")
    val popularMovieResults: List<PopularMovieResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)