package com.dhruv.moviesapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStores.of
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhruv.moviesapp.R
import com.dhruv.moviesapp.data.api.MovieDBClient
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.repository.NetworkState
import com.dhruv.moviesapp.fragments.popular_movies.PopularMoviePagedListAdapter
import com.dhruv.moviesapp.fragments.popular_movies.PopularMoviePagedListRepository
import com.dhruv.moviesapp.fragments.popular_movies.PopularMovieViewModel
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import java.util.EnumSet.of
import java.util.Map.of

class PopularMoviesFragment : Fragment() {

    private lateinit var viewModel: PopularMovieViewModel
    lateinit var  movieRepository: PopularMoviePagedListRepository
    lateinit var pmvl: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiService : MovieDBInterface = MovieDBClient.getClient()
        movieRepository =   PopularMoviePagedListRepository(apiService)
        viewModel = getViewModel()
        val movieAdapter  = PopularMoviePagedListAdapter(this.requireContext())
        val gridLayout = GridLayoutManager(this.requireContext(),1)

        pmvl = view.findViewById(R.id.popular_movie_list)
        pmvl.layoutManager = gridLayout
        pmvl.setHasFixedSize(true)
        pmvl.adapter = movieAdapter

        viewModel.moviePagedList.observe(viewLifecycleOwner, Observer {
            movieAdapter.submitList(it)
        })
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            popular_fragment_pgb.visibility = if(viewModel.listisEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            popular_fragment_text.visibility = if(viewModel.listisEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listisEmpty()){
                movieAdapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel(): PopularMovieViewModel {
        return ViewModelProviders.of(this@PopularMoviesFragment, object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PopularMovieViewModel(movieRepository) as T
            }
        })[PopularMovieViewModel::class.java]
    }

}