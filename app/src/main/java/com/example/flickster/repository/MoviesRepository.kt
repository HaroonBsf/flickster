package com.example.flickster.repository

import com.example.flickster.moviesmodel.PopularMoviesResponse
import com.example.flickster.retrofit.MoviesAPInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(private val apInterface: MoviesAPInterface) {

    suspend fun fetchPopularMovies(page: Int): PopularMoviesResponse? {
        return withContext(Dispatchers.IO) {
            val response = apInterface.getPopularMovies(page).execute()
            if (response.isSuccessful) response.body() else null
        }
    }

    suspend fun fetchTrendingMovies(page: Int): PopularMoviesResponse? {
        return withContext(Dispatchers.IO) {
            val response = apInterface.getTrendingMovies(page).execute()
            if (response.isSuccessful) response.body() else null
        }
    }

}