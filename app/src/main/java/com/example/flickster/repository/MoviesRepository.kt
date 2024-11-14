package com.example.flickster.repository

import android.util.Log
import com.example.flickster.moviesmodel.PopularMoviesResponse
import com.example.flickster.retrofit.MoviesAPInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.await

class MoviesRepository(private val apInterface: MoviesAPInterface) {

    suspend fun fetchPopularMovies(page: Int): PopularMoviesResponse? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("THREAD", "Repository Popular-${Thread.currentThread().name} thread")
                apInterface.getPopularMovies(page)
            } catch (e: Exception){
                throw e
            }
        }
    }

    suspend fun fetchTrendingMovies(page: Int): PopularMoviesResponse? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("THREAD", "Repository Trending-${Thread.currentThread().name} thread")
                apInterface.getTrendingMovies(page)
            } catch (e: Exception) {
                throw e
            }
        }
    }

}