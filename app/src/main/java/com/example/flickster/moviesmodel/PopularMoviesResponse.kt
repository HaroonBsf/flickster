package com.example.flickster.moviesmodel

data class PopularMoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Long,
    val total_results: Long,
)