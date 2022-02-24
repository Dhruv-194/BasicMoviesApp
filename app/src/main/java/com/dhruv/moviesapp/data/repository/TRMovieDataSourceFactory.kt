package com.dhruv.moviesapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.vo.PopularMovieResult
import com.dhruv.moviesapp.data.vo.TRMovieResult
import io.reactivex.rxjava3.disposables.CompositeDisposable

class TRMovieDataSourceFactory (private val apiService: MovieDBInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, TRMovieResult>(){
    val moviesLiveDataSource = MutableLiveData<TRMovieDatasource>()
    override fun create(): DataSource<Int, TRMovieResult> {
        val movieDataSource =  TRMovieDatasource(apiService,compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}