package com.dhruv.moviesapp.data.vo


import com.google.gson.annotations.SerializedName

data class UpcomingMovieResponse(
    val page: Int,
    @SerializedName("results")
    val upcomingMovieResults: List<UpcomingMovieResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)