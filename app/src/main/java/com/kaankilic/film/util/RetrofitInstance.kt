package com.kaankilic.film.util

import com.kaankilic.film.service.MovieAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: MovieAPI by lazy {
        retrofit.create(MovieAPI::class.java)
    }
}