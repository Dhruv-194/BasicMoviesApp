package com.dhruv.moviesapp.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dhruv.moviesapp.data.repository.NetworkState
import com.dhruv.moviesapp.data.vo.MovieDetails
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MovieViewModel(private val movieRepo:MovieDetailsRepository, movieId:Int) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val movieDetails : LiveData<MovieDetails> by lazy {
        movieRepo.fetchMovieDetails(compositeDisposable, movieId)
    }
    val networkState : LiveData<NetworkState> by lazy {
        movieRepo.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}