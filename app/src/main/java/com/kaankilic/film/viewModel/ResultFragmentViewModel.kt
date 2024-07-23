package com.kaankilic.film.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.kaankilic.film.RoomDB.MovieDatabase
import com.kaankilic.film.model.Movie
import kotlinx.coroutines.launch

class ResultFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val filmmutable = MutableLiveData<List<Movie>>()

    fun roomVerisiAl(uuid : Int){
        viewModelScope.launch {
            val dao = MovieDatabase(getApplication()).movieDao()
            val movie = dao.getAllMovie()
            filmmutable.value=movie
        }


    }
}