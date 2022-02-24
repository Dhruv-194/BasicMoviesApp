package com.dhruv.moviesapp.fragments.popular_movies

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhruv.moviesapp.R
import com.dhruv.moviesapp.data.api.IMAGE_BASE_URL
import com.dhruv.moviesapp.data.repository.NetworkState
import com.dhruv.moviesapp.data.vo.MovieDetails
import com.dhruv.moviesapp.data.vo.PopularMovieResult
import com.dhruv.moviesapp.movie_details.MovieDetailsActivity
import kotlinx.android.synthetic.main.network_state_item.view.*
import kotlinx.android.synthetic.main.popular_movie_list_item.view.*

class PopularMoviePagedListAdapter(public val context: Context) : PagedListAdapter<PopularMovieResult, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState:NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater  = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE){
            view  = layoutInflater.inflate(R.layout.popular_movie_list_item,parent,false)
            return MovieItemViewHolder(view)
        }else {
            view = layoutInflater.inflate(R.layout.network_state_item,parent,false)
            return NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE){
            (holder as MovieItemViewHolder).bind(getItem(position),context)
        }
        else{
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }

    }

    private fun hasExtraRow():Boolean{
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount-1){
            NETWORK_VIEW_TYPE
        }else {
            MOVIE_VIEW_TYPE
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<PopularMovieResult>(){
        override fun areItemsTheSame(
            oldItem: PopularMovieResult,
            newItem: PopularMovieResult
        ): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PopularMovieResult,
            newItem: PopularMovieResult
        ): Boolean {
            return oldItem==newItem
        }

    }

    class MovieItemViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(movie: PopularMovieResult?, context: Context){
            itemView.pm_movie_title.text = movie?.originalTitle
            itemView.pm_popularity.text = movie?.popularity.toString()
            itemView.pm_overview.text = movie?.overview

            val moviePosterURL = IMAGE_BASE_URL + movie?.posterPath
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.pm_poster)

            itemView.setOnClickListener {
                val intent = Intent(context, MovieDetailsActivity::class.java)
                intent.putExtra("id", movie?.id)
                context.startActivity(intent)
            }
        }

    }
        class NetworkStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
            fun bind(networkState: NetworkState?){
                if (networkState!=null && networkState == NetworkState.LOADING){
                    itemView.progress_bar_item.visibility = View.VISIBLE
                } else
                {
                    itemView.progress_bar_item.visibility = View.GONE
                }
                if (networkState!=null && networkState == NetworkState.ERROR){
                    itemView.error_msg_item.visibility = View.VISIBLE
                    itemView.error_msg_item.text = networkState.msg
                }
                else if (networkState!=null && networkState == NetworkState.ENDOFLIST){
                    itemView.error_msg_item.visibility = View.VISIBLE
                    itemView.error_msg_item.text = networkState.msg
                }
                else{
                    itemView.error_msg_item.visibility = View.GONE
                }
            }
        }

    fun setNetworkState(newNetworkState: NetworkState){
        val previousNetworkState : NetworkState? = this.networkState
        val hadExtraRow:Boolean = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow:Boolean = hasExtraRow()
        if (hadExtraRow != hasExtraRow){
            if (hadExtraRow){
                notifyItemRemoved(super.getItemCount())
            }else{
                notifyItemInserted(super.getItemCount())
            }
        }else if (hasExtraRow && previousNetworkState != newNetworkState){
            notifyItemChanged(itemCount-1)
        }
    }
}