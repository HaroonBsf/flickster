package com.example.flickster

import com.example.flickster.retrofit.MoviesAPInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.themoviedb.org/"
    private const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYWFiZWZjNTIxMjQ2NGY3YmRkOWRlN2FjNjJmZDI5YyIsIm5iZiI6MTcyNzYyNzgxMC45Mjk4MjIsInN1YiI6IjY2ZjY4ODI5NmM5YTY4MTU1MDcwYzI4YyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.AY6_7kkR757IkG19ULkyzUwVaQZ6ECcCgPEXALObk9g"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain: Interceptor.Chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $API_KEY")
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getMoviesAPi(): MoviesAPInterface{
        return retrofit.create(MoviesAPInterface::class.java)
    }
}