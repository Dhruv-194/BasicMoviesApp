package com.dhruv.moviesapp.fragments.tr_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.api.POST_PAGE
import com.dhruv.moviesapp.data.repository.*
import com.dhruv.moviesapp.data.vo.TRMovieResult
import io.reactivex.rxjava3.disposables.CompositeDisposable

class TRMoviePagedListRepository(private val apiService: MovieDBInterface) {
    private lateinit var moviePagedList: LiveData<PagedList<TRMovieResult>>
    lateinit var moviesDataSourceFactory: TRMovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<TRMovieResult>> {
        moviesDataSourceFactory = TRMovieDataSourceFactory(apiService, compositeDisposable)

        val config : PagedList.Config= PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PAGE)
            .build()
        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList
    }
    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<TRMovieDatasource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, TRMovieDatasource::networkState
        )
    }
}