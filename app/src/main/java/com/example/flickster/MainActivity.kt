package com.example.flickster

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flickster.adapter.MoviesAdapter
import com.example.flickster.databinding.ActivityMainBinding
import com.example.flickster.repository.MoviesRepository
import com.example.flickster.repository.MoviesViewModel
import com.example.flickster.repository.MoviesViewModelFactory
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val moviesViewModel: MoviesViewModel by viewModels {
        MoviesViewModelFactory(MoviesRepository(RetrofitClient.getMoviesAPi()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTrendingSlider.layoutManager = layoutManager
        binding.rvTrendingSlider.setHasFixedSize(true)
        val layoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTrending.layoutManager = layoutManager1
        binding.rvTrending.setHasFixedSize(true)
        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopular.layoutManager = layoutManager2
        binding.rvPopular.setHasFixedSize(true)

        moviesViewModel.getPopularMovies(1)
        moviesViewModel.getTrendingMovies(1)

        moviesViewModel.popularMovies.observe(this, Observer { response ->
            response?.let {
                val adapter = MoviesAdapter(this, it.results, "popular")
                binding.rvPopular.adapter = adapter
            }
        })
        moviesViewModel.trendingMovies.observe(this, Observer { response ->
            response?.let {
                val adapter = MoviesAdapter(this, it.results, "popular")
                binding.rvTrending.adapter = adapter
                val sliderAdapter = MoviesAdapter(this, it.results, "trending")
                binding.rvTrendingSlider.adapter = sliderAdapter
            }
        })
    }
}