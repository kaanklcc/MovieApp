package com.kaankilic.film.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val results : List<MovieResults>
)
