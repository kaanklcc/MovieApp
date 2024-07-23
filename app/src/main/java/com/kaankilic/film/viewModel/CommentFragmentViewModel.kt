package com.kaankilic.film.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaankilic.film.RoomDB.MovieDatabase
import com.kaankilic.film.model.Movie
import kotlinx.coroutines.launch

class CommentFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val movieLive = MutableLiveData<Movie>()

    fun roomVerisiAl(uuid: Int) {
        viewModelScope.launch {
            val dao = MovieDatabase(getApplication()).movieDao()
            val movie = dao.getMovie(uuid)
            movieLive.value = movie
        }
    }

    fun saveMovie(movie: Movie) {
        viewModelScope.launch {
            val dao = MovieDatabase(getApplication()).movieDao()
            dao.insertMovie(movie)
            movieLive.postValue(movie)
        }
    }
    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            val dao = MovieDatabase(getApplication()).movieDao()
            dao.deleteMovie(movie)

        }
    }


}