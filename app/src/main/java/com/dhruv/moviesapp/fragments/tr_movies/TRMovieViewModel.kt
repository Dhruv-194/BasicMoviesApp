package com.dhruv.moviesapp.fragments.tr_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dhruv.moviesapp.data.repository.NetworkState

import com.dhruv.moviesapp.data.vo.TRMovieResult
import io.reactivex.rxjava3.disposables.CompositeDisposable

class TRMovieViewModel(private val movieRepository: TRMoviePagedListRepository): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    internal val moviePagedList : LiveData<PagedList<TRMovieResult>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }
    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }
    fun listisEmpty(): Boolean{
        return moviePagedList.value?.isEmpty()?:true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}