package com.dhruv.moviesapp.fragments.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dhruv.moviesapp.data.repository.NetworkState
import com.dhruv.moviesapp.data.vo.PopularMovieResult
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.http.Query

class PopularMovieViewModel (private val movieRepository: PopularMoviePagedListRepository): ViewModel(){
    private val compositeDisposable = CompositeDisposable()
    val moviePagedList : LiveData<PagedList<PopularMovieResult>> by lazy {
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

    /*fun searchMovie(query: String){
       PopularMovieViewModel.moviePagedList.value = query
    }*/
}