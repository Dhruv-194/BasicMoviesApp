package com.dhruv.moviesapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.dhruv.moviesapp.data.api.FIRST_PAGE
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.vo.PopularMovieResult
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class PopularMovieDatasource(private val apiService : MovieDBInterface, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, PopularMovieResult>() {

    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PopularMovieResult>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.popularMovieResults, null,page+1)
                    networkState.postValue(NetworkState.LOADED)
                },{
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("PopularMovieDataSource", it.message.toString())
                })
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, PopularMovieResult>
    ) {

        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.totalPages>= params.key){
                        callback.onResult(it.popularMovieResults, params.key+1)
                        networkState.postValue(NetworkState.LOADED)
                    }else {
                        networkState.postValue(NetworkState.ENDOFLIST)
                    }
                },{
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("PopularMovieDataSource", it.message.toString())
                })
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, PopularMovieResult>
    ) {

    }

}