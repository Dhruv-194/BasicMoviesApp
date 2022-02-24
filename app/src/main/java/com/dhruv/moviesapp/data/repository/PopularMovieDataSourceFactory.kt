package com.dhruv.moviesapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.vo.PopularMovieResult
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PopularMovieDataSourceFactory(private val apiService:MovieDBInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int,PopularMovieResult>() {
    val moviesLiveDataSource = MutableLiveData<PopularMovieDatasource>()
    override fun create(): DataSource<Int, PopularMovieResult> {
       val movieDataSource =  PopularMovieDatasource(apiService,compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

}