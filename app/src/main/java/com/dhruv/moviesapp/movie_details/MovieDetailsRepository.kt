package com.dhruv.moviesapp.movie_details

import androidx.lifecycle.LiveData
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.repository.MovieDetailsNetworkDataSource
import com.dhruv.moviesapp.data.repository.NetworkState
import com.dhruv.moviesapp.data.vo.MovieDetails
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService : MovieDBInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchMovieDetails(compositeDisposable: CompositeDisposable, movieId:Int) : LiveData<MovieDetails>{
         movieDetailsNetworkDataSource=MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadMovieDetails
    }

    fun getMovieDetailsNetworkState() :LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }
//cache the data in local storage

}