package com.kaankilic.film.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaankilic.film.RoomDB.MovieDatabase
import com.kaankilic.film.model.Movie
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application)  : AndroidViewModel(application){
    val movieLiveData = MutableLiveData<Movie>()

    fun roomVerisiniAL(uuid: Int){
        viewModelScope.launch {
            val dao = MovieDatabase(getApplication()).movieDao()
            val movie = dao.getMovie(uuid)
            movieLiveData.value=movie
        }
    }
}