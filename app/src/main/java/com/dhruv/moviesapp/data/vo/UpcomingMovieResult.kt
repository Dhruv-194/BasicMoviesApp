package com.dhruv.moviesapp.data.vo


import com.google.gson.annotations.SerializedName

data class UpcomingMovieResult(
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,


)