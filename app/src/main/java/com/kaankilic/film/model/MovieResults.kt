package com.kaankilic.film.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MovieResults(
    @SerializedName("id")
    var id : String?,

    @SerializedName("original_title")
    var originalTitle : String?,

    @SerializedName("original_language")
    var originalLanguage : String?,

    @SerializedName("overview")
    var overview : String?,

    @SerializedName("poster_path")
    var posterPath : String?,


    @SerializedName("release_date")
    var releaseDate : String?,




){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}
