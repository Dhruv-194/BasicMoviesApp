package com.dhruv.moviesapp.fragments.upcoming_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.api.POST_PAGE
import com.dhruv.moviesapp.data.repository.*

import com.dhruv.moviesapp.data.vo.UpcomingMovieResult
import io.reactivex.rxjava3.disposables.CompositeDisposable

class UpcomingMoviePagedListRepository(private val apiService: MovieDBInterface) {
    lateinit var moviePagedList: LiveData<PagedList<UpcomingMovieResult>>
    lateinit var moviesDataSourceFactory: UpcomingMovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<UpcomingMovieResult>> {
        moviesDataSourceFactory = UpcomingMovieDataSourceFactory(apiService, compositeDisposable)

        val config : PagedList.Config= PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PAGE)
            .build()
        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList
    }
    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<UpcomingMovieDatasource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, UpcomingMovieDatasource::networkState
        )
    }
}