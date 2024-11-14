package com.example.flickster.retrofit

import com.example.flickster.moviesmodel.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPInterface {

    @GET("3/trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int
    )
            : PopularMoviesResponse

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    )
            : PopularMoviesResponse
}