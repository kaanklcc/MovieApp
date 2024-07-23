package com.kaankilic.film.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @ColumnInfo(name = "id")
    var id : String?,

    @ColumnInfo(name = "original_title")
    var originalTitle : String?,

    @ColumnInfo(name = "original_language")
    var originalLanguage : String?,

    @ColumnInfo(name="overview")
    var overview : String?,

    @ColumnInfo(name="poster_path")
    var posterPath : String?,

    @ColumnInfo(name="release_date")
    var releaseDate : String?,



){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}
