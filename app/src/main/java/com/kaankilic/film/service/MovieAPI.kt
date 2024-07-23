package com.kaankilic.film.service

import com.kaankilic.film.model.Movie
import com.kaankilic.film.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie/popular")
    suspend fun getMovie(@Query("api_key") apiKey: String): Response<MovieResponse>
}