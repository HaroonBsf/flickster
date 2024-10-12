package com.example.flickster.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickster.moviesmodel.PopularMoviesResponse
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val _popularMovies = MutableLiveData<PopularMoviesResponse>()
    val popularMovies: LiveData<PopularMoviesResponse> get() = _popularMovies

    private val _trendingMovies = MutableLiveData<PopularMoviesResponse>()
    val trendingMovies: LiveData<PopularMoviesResponse> get() = _trendingMovies

    fun getPopularMovies(page: Int) {
        viewModelScope.launch {
            val response = repository.fetchPopularMovies(page)
            response?.let {
                _popularMovies.postValue(it)
            }
        }
    }

    fun getTrendingMovies(page: Int) {
        viewModelScope.launch {
            val response = repository.fetchTrendingMovies(page)
            response?.let {
                _trendingMovies.postValue(it)
            }
        }
    }

}