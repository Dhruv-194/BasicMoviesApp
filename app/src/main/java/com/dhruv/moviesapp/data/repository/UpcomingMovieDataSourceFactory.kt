package com.dhruv.moviesapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.vo.UpcomingMovieResult
import io.reactivex.rxjava3.disposables.CompositeDisposable

class UpcomingMovieDataSourceFactory(private val apiService: MovieDBInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, UpcomingMovieResult>(){
     val moviesLiveDataSource = MutableLiveData<UpcomingMovieDatasource>()
    override fun create(): DataSource<Int, UpcomingMovieResult> {
        val movieDataSource =  UpcomingMovieDatasource(apiService,compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}