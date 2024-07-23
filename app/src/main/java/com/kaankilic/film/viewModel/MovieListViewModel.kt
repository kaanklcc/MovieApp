package com.kaankilic.film.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaankilic.film.RoomDB.MovieDatabase
import com.kaankilic.film.model.Movie
import com.kaankilic.film.model.MovieResponse
import com.kaankilic.film.service.MovieAPIServis
import com.kaankilic.film.util.OzelSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(application: Application) : AndroidViewModel(application) {
    val Movie = MutableLiveData<List<Movie>>()
    val movieError = MutableLiveData<Boolean>()
    val movieLoading = MutableLiveData<Boolean>()

    private val movieAPIServis = MovieAPIServis()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())
    private val guncellemeZamani= 10*60*1000*1000*1000L

    fun refreshData(){
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAL()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            verileriRoomdanAL()
        }else{
            verileriInternettenAl()
        }
    }
    fun refreshDataFromIntarnet(){
        verileriInternettenAl()
    }
    fun searchMovies(query: String) {
        viewModelScope.launch {
            val movieList = MovieDatabase(getApplication()).movieDao().getAllMovie()
            val filteredList = movieList.filter { movie ->
                movie.originalTitle?.contains(query, ignoreCase = true) ?: false
            }
            movieleriGöster(filteredList)
        }
    }




    private fun verileriInternettenAl() {
        movieLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = movieAPIServis.getData("aaaaf180f736635b6d7b6e158b4b8da0")
                if (response.isSuccessful) {
                    response.body()?.let { movieResponse ->
                        val movieList = movieResponse.results.map {
                            Movie(
                                id = it.id,
                                originalTitle = it.originalTitle,
                                originalLanguage = it.originalLanguage,
                                overview = it.overview,
                                posterPath = it.posterPath,
                                releaseDate = it.releaseDate,


                            )
                        }
                        withContext(Dispatchers.Main) {
                            roomaKaydet(movieList)
                            Toast.makeText(getApplication(), "Verileri internetten aldık.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        movieLoading.value = false
                        movieError.value = true
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    movieLoading.value = false
                    movieError.value = true
                    e.printStackTrace()
                }
            }
        }
    }


    private fun verileriRoomdanAL() {
        movieLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val movieListesi = MovieDatabase(getApplication()).movieDao().getAllMovie()
            withContext(Dispatchers.Main) {
                movieleriGöster(movieListesi)
                Toast.makeText(getApplication(), "Roomdan Aldık.", Toast.LENGTH_SHORT).show()

            }
        }
    }
    private fun movieleriGöster(movieListesi: List<Movie>) {
        Movie.value = movieListesi
        movieLoading.value = false
        movieError.value = false

    }
    private fun roomaKaydet(movieListesi: List<Movie>) {
        viewModelScope.launch {
            val dao = MovieDatabase(getApplication()).movieDao()
            dao.deleteAll()
            val uuidListesi = dao.insertAll(*movieListesi.toTypedArray())
            var i = 0
            while (i < movieListesi.size) {
                movieListesi[i].uuid = uuidListesi[i].toInt()
                i = i + 1
            }
            movieleriGöster(movieListesi)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }
}