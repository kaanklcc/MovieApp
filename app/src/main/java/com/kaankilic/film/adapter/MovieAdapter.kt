package com.kaankilic.film.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kaankilic.film.View.MovieListFragmentDirections
import com.kaankilic.film.databinding.MovieRecyclerRowBinding
import com.kaankilic.film.model.Movie
import com.kaankilic.film.util.gorselIndir
import com.kaankilic.film.util.placeHolderYap

class MovieAdapter( val movieListesi : ArrayList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    class MovieViewHolder(val binding : MovieRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  movieListesi.size
    }
    fun movieListesiniGuncelle(yeniMovieListesi : List<Movie>){
        movieListesi.clear()
        movieListesi.addAll(yeniMovieListesi)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.isim.text=movieListesi[position].originalTitle
        holder.binding.year.text=movieListesi[position].releaseDate

        holder.itemView.setOnClickListener {
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movieListesi[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        //holder.binding.imageView.gorselIndir(movieListesi[position].posterPath, placeHolderYap(holder.itemView.context))
        holder.binding.imageView.gorselIndir("https://image.tmdb.org/t/p/w500/${movieListesi[position].posterPath}", placeHolderYap(holder.itemView.context))


    }
}