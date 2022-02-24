package com.dhruv.moviesapp.fragments.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.api.POST_PAGE
import com.dhruv.moviesapp.data.repository.NetworkState
import com.dhruv.moviesapp.data.repository.PopularMovieDataSourceFactory
import com.dhruv.moviesapp.data.repository.PopularMovieDatasource
import com.dhruv.moviesapp.data.vo.PopularMovieResult
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PopularMoviePagedListRepository (private val apiService: MovieDBInterface){
    lateinit var moviePagedList: LiveData<PagedList<PopularMovieResult>>
    lateinit var moviesDataSourceFactory: PopularMovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable):LiveData<PagedList<PopularMovieResult>>{
        moviesDataSourceFactory = PopularMovieDataSourceFactory(apiService, compositeDisposable)

        val config : PagedList.Config= PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PAGE)
            .build()
        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList
    }
    fun getNetworkState():LiveData<NetworkState>{
        return Transformations.switchMap<PopularMovieDatasource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, PopularMovieDatasource::networkState
        )
    }
}