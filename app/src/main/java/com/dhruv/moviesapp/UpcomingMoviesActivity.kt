package com.dhruv.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dhruv.moviesapp.data.api.MovieDBClient
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.repository.NetworkState

import com.dhruv.moviesapp.fragments.upcoming_movies.UpcomingMoviePagedListAdapter
import com.dhruv.moviesapp.fragments.upcoming_movies.UpcomingMoviePagedListRepository
import com.dhruv.moviesapp.fragments.upcoming_movies.UpcomingMovieViewModel

import kotlinx.android.synthetic.main.activity_upcoming_movies.*

class UpcomingMoviesActivity : AppCompatActivity() {
    private lateinit var viewModel: UpcomingMovieViewModel
    lateinit var  movieRepository: UpcomingMoviePagedListRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_movies)
        val apiService : MovieDBInterface = MovieDBClient.getClient()
        movieRepository =   UpcomingMoviePagedListRepository(apiService)
        viewModel = getViewModel()
        val movieAdapter = UpcomingMoviePagedListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this,1)
        /*tempArrayList = arrayListOf()
        tempArrayList.add(viewModel)*/


        upcoming_movie_list.layoutManager = gridLayoutManager
        upcoming_movie_list.setHasFixedSize(true)
        upcoming_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })
        viewModel.networkState.observe(this, Observer {
            upcoming_activity_pgb.visibility = if(viewModel.listisEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            upcoming_activity_text.visibility = if(viewModel.listisEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listisEmpty()){
                movieAdapter.setNetworkState(it)
            }
        })
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {
            tempArrayList.clear()
            val searchText = p0!!.toString().lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    viewModel.forEach
                }

                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }*/


    private fun getViewModel(): UpcomingMovieViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return UpcomingMovieViewModel(movieRepository) as T
            }
        })[UpcomingMovieViewModel::class.java]
    }
}