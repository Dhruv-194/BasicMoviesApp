package com.dhruv.moviesapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.dhruv.moviesapp.data.api.FIRST_PAGE
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.vo.UpcomingMovieResult
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class UpcomingMovieDatasource(private val apiService : MovieDBInterface, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, UpcomingMovieResult>(){
    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UpcomingMovieResult>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getUpcomingMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.upcomingMovieResults, null,page+1)
                    networkState.postValue(NetworkState.LOADED)
                },{
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("UpcomingMovieDataSource", it.message.toString())
                })
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, UpcomingMovieResult>
    ) {

        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getUpcomingMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.totalPages>= params.key){
                        callback.onResult(it.upcomingMovieResults, params.key+1)
                        networkState.postValue(NetworkState.LOADED)
                    }else {
                        networkState.postValue(NetworkState.ENDOFLIST)
                    }
                },{
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("UpcomingMovieDataSource", it.message.toString())
                })
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, UpcomingMovieResult>
    ) {

    }
}