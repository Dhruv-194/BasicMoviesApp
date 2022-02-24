package com.dhruv.moviesapp.data.api

import com.dhruv.moviesapp.data.vo.MovieDetails
import com.dhruv.moviesapp.data.vo.PopularMovieResponse
import com.dhruv.moviesapp.data.vo.TRMovieResponse
import com.dhruv.moviesapp.data.vo.UpcomingMovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBInterface {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page")page:Int): Single<PopularMovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")id:Int): Single<MovieDetails>

    @GET("movie/top_rated")
    fun getTRMovie(@Query("page")page: Int):Single<TRMovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovie(@Query("page")page: Int):Single<UpcomingMovieResponse>
}