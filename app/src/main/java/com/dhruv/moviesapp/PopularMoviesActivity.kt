package com.dhruv.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dhruv.moviesapp.data.api.MovieDBClient
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.repository.NetworkState
import com.dhruv.moviesapp.fragments.popular_movies.PopularMoviePagedListAdapter
import com.dhruv.moviesapp.fragments.popular_movies.PopularMoviePagedListRepository
import com.dhruv.moviesapp.fragments.popular_movies.PopularMovieViewModel
import kotlinx.android.synthetic.main.activity_popular_movies.*
import java.util.*
import kotlin.collections.ArrayList

class PopularMoviesActivity : AppCompatActivity() {
    private lateinit var viewModel: PopularMovieViewModel
    lateinit var  movieRepository: PopularMoviePagedListRepository
    /*private lateinit var tempArrayList:ArrayList<PopularMovieViewModel>*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_movies)

        val apiService : MovieDBInterface = MovieDBClient.getClient()
        movieRepository =   PopularMoviePagedListRepository(apiService)
        viewModel = getViewModel()
        val movieAdapter = PopularMoviePagedListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this,1)
        /*tempArrayList = arrayListOf()
        tempArrayList.add(viewModel)*/


        popular_movie_list.layoutManager = gridLayoutManager
        popular_movie_list.setHasFixedSize(true)
        popular_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })
        viewModel.networkState.observe(this, Observer {
            popular_activity_pgb.visibility = if(viewModel.listisEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            popular_activity_text.visibility = if(viewModel.listisEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

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


    private fun getViewModel(): PopularMovieViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PopularMovieViewModel(movieRepository) as T
            }
        })[PopularMovieViewModel::class.java]
    }
}