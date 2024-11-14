package com.example.flickster.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.flickster.databinding.RvPopularItemBinding
import com.example.flickster.databinding.RvTrendingSliderBinding
import com.example.flickster.moviesmodel.Result

class MoviesAdapter(
    private val context: Context,
    private val movies: List<Result>,
    private val rvType: String
) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(
        val binding: ViewDataBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        return when (rvType) {
            "trending" -> {
                val binding = RvTrendingSliderBinding.inflate(layoutInflater, parent, false)
                MovieViewHolder(binding)
            }

            "popular" -> {
                val binding = RvPopularItemBinding.inflate(layoutInflater, parent, false)
                MovieViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        when (holder.binding) {
            is RvTrendingSliderBinding -> {
                holder.binding.movie = movie
            }

            is RvPopularItemBinding -> {
                (holder.binding as RvPopularItemBinding).popularMovies = movie
            }
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}