package com.example.flickster.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MoviesViewModelFactory(private val repository: MoviesRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MoviesViewModel::class.java)){
            MoviesViewModel(repository) as T
        } else{
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

}