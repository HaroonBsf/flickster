package com.example.flickster

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.flickster.adapter.MoviesAdapter
import com.example.flickster.databinding.ActivityMainBinding
import com.example.flickster.moviesmodel.PopularMoviesResponse
import com.example.flickster.retrofit.MoviesAPInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Timer
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var moviesAdapter: MoviesAdapter

    private val apiKey =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYWFiZWZjNTIxMjQ2NGY3YmRkOWRlN2FjNjJmZDI5YyIsIm5iZiI6MTcyNzYyNzgxMC45Mjk4MjIsInN1YiI6IjY2ZjY4ODI5NmM5YTY4MTU1MDcwYzI4YyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.AY6_7kkR757IkG19ULkyzUwVaQZ6ECcCgPEXALObk9g"

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
        binding.rvTrending.layoutManager = layoutManager
        binding.rvTrending.setHasFixedSize(true)
        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopular.layoutManager = layoutManager2
        binding.rvPopular.setHasFixedSize(true)

        val client = OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $apiKey")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesAPInterface::class.java)


        getTrendingMovies(retrofit, layoutManager)
        getPopularMovies(retrofit)


    }

    private fun getPopularMovies(retrofit: MoviesAPInterface) {
        val call = retrofit.getPopularMovies(1)
        call.enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    moviesAdapter = MoviesAdapter(this@MainActivity, movies, "popular")
                    binding.rvPopular.adapter = moviesAdapter
                }
            }

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Log.e("Error", "Failed to fetch movies: ${t.message}")
            }
        })
    }

    private fun getTrendingMovies(retrofit: MoviesAPInterface, layoutManager: LinearLayoutManager) {
        val call = retrofit.getTrendingMovies(1)
        call.enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    moviesAdapter = MoviesAdapter(this@MainActivity, movies, "trending")
                    binding.rvTrending.adapter = moviesAdapter

                    val snapHelper = LinearSnapHelper()
                    snapHelper.attachToRecyclerView(binding.rvTrending)
                    val timer = Timer()
                    timer.schedule(timerTask {
                        if (layoutManager.findLastCompletelyVisibleItemPosition() < (moviesAdapter.itemCount - 1)) {
                            layoutManager.smoothScrollToPosition(
                                binding.rvTrending,
                                RecyclerView.State(),
                                layoutManager.findLastCompletelyVisibleItemPosition() + 1
                            )
                        } else {
                            layoutManager.smoothScrollToPosition(
                                binding.rvTrending,
                                RecyclerView.State(),
                                0
                            )
                        }
                    }, 0, 3000)
                }
            }

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Log.e("Error", "Failed to fetch movies: ${t.message}")
            }
        })
    }
}