package com.example.flickster.retrofit

import com.example.flickster.moviesmodel.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPInterface {

    @GET("3/trending/movie/day")
    fun getTrendingMovies(
        @Query("page") page: Int
    )
            : retrofit2.Call<PopularMoviesResponse>

    @GET("3/movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int
    )
            : retrofit2.Call<PopularMoviesResponse>

}