package com.example.flickster

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.flickster.adapter.MoviesAdapter
import com.example.flickster.databinding.ActivityMainBinding
import com.example.flickster.repository.MoviesRepository
import com.example.flickster.repository.MoviesViewModel
import com.example.flickster.repository.MoviesViewModelFactory
import java.util.Timer
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val moviesViewModel: MoviesViewModel by viewModels {
        MoviesViewModelFactory(MoviesRepository(RetrofitClient.getMoviesAPi()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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

                /*val snapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(binding.rvTrendingSlider)
                val timer = Timer()
                timer.schedule(timerTask {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() < (adapter.itemCount - 1)) {
                        layoutManager.smoothScrollToPosition(
                            binding.rvTrendingSlider,
                            RecyclerView.State(),
                            layoutManager.findLastCompletelyVisibleItemPosition() + 1
                        )
                    } else {
                        layoutManager.smoothScrollToPosition(
                            binding.rvTrendingSlider,
                            RecyclerView.State(),
                            0
                        )
                    }
                }, 0, 3000)*/

            }
        })
    }
}