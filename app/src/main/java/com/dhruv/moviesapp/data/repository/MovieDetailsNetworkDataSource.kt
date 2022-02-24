package com.dhruv.moviesapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.vo.MovieDetails
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.internal.disposables.ArrayCompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

import java.lang.Exception

class MovieDetailsNetworkDataSource(private val apiService: MovieDBInterface, private val compositeDisposable: CompositeDisposable) {
private val _networkState = MutableLiveData<NetworkState>()
    val networkState:LiveData<NetworkState>
         get() = _networkState
private val _downloadMovieDetails = MutableLiveData<MovieDetails>()
    val downloadMovieDetails:LiveData<MovieDetails>
        get() = _downloadMovieDetails

    fun fetchMovieDetails(movieId:Int){
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                        _downloadMovieDetails.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },{
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsDataSource", it.message.toString())
                        }
                    )
            )
        }catch (e:Exception){
            Log.e("MovieDetailsDataSource", e.message.toString())
        }
    }
}