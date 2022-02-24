package com.dhruv.moviesapp.movie_details

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dhruv.moviesapp.R
import com.dhruv.moviesapp.data.api.IMAGE_BASE_URL
import com.dhruv.moviesapp.data.api.MovieDBClient
import com.dhruv.moviesapp.data.api.MovieDBInterface
import com.dhruv.moviesapp.data.repository.NetworkState
import com.dhruv.moviesapp.data.vo.MovieDetails
import com.dhruv.moviesapp.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val movieId : Int = intent.getIntExtra("id",1)
        val apiService: MovieDBInterface = MovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)
        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })
        viewModel.networkState.observe(this, Observer {
            binding.pgBar.visibility = if(it == NetworkState.LOADING)View.VISIBLE else View.GONE
            binding.textError.visibility = if(it==NetworkState.ERROR)View.VISIBLE else View.GONE
        })
    }
        fun bindUI(it:MovieDetails){
            binding.movieTitle.text = it.title
            binding.movieTagline.text=it.tagline
            binding.movieReleaseDate.text=it.releaseDate
            binding. moviePopularity.text= it.popularity.toString()
            binding.movieLanguage.text=it.originalLanguage
            binding.movieBudget.text=it.budget.toString()
            binding.movieRevenue.text=it.revenue.toString()
            binding.movieVoteAvg.text=it.voteAverage.toString()
            binding.movieVoteCount.text=it.voteCount.toString()
            binding.movieOverview.text=it.overview
            binding.movieGenre.text=it.genres.toString()
            val moviePosterURL  = IMAGE_BASE_URL + it.posterPath
            Glide.with(this).load(moviePosterURL).into(binding.moviePoster)
            val homepageURL = it.homepage
            binding.movieHomepage.setOnClickListener{
                val intent = Intent(android.content.Intent.ACTION_VIEW)
                intent.data = Uri.parse(homepageURL)
                startActivity(intent)
            }

        }

    private fun getViewModel(movieId:Int):MovieViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(movieRepository,movieId) as T
            }
        })[MovieViewModel::class.java]

}



}


