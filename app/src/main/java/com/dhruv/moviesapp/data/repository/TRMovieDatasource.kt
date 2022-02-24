package com.dhruv.moviesapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.dhruv.moviesapp.data.api.FIRST_PAGE
import com.dhruv.moviesapp.data.api.MovieDBInterface

import com.dhruv.moviesapp.data.vo.TRMovieResult
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TRMovieDatasource(private val apiService : MovieDBInterface, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, TRMovieResult>() {
    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TRMovieResult>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getTRMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.TRMovieResults, null,page+1)
                    networkState.postValue(NetworkState.LOADED)
                },{
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("TRMovieDataSource", it.message.toString())
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TRMovieResult>) {

        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getTRMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.totalPages>= params.key){
                        callback.onResult(it.TRMovieResults, params.key+1)
                        networkState.postValue(NetworkState.LOADED)
                    }else {
                        networkState.postValue(NetworkState.ENDOFLIST)
                    }
                },{
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("TRMovieDataSource", it.message.toString())
                })
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TRMovieResult>
    ) {

    }

}