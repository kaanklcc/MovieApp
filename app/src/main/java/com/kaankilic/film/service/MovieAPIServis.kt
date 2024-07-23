package com.kaankilic.film.service

import com.kaankilic.film.util.RetrofitInstance
import com.kaankilic.film.model.MovieResponse
import retrofit2.Response

class MovieAPIServis {
    private val api = RetrofitInstance.api

    suspend fun getData(apiKey: String): Response<MovieResponse> {
        return api.getMovie(apiKey)
    }
}