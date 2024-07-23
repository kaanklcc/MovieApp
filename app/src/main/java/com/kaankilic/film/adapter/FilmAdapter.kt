package com.kaankilic.film.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kaankilic.film.View.MyResultFragmentDirections
import com.kaankilic.film.databinding.FilmRecyclerRowBinding
import com.kaankilic.film.model.Movie

class FilmAdapter(val filmListesi : ArrayList<Movie>) : RecyclerView.Adapter<FilmAdapter.FilmViewHolder> (){
    class FilmViewHolder( val binding : FilmRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = FilmRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilmViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filmListesi.size
    }
    fun updateMovieList(newMovieList: List<Movie>) {
        filmListesi.clear()
        filmListesi.addAll(newMovieList)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.binding.recyclerViewTextView2.text=filmListesi[position].originalTitle
        holder.itemView.setOnClickListener {
            val action= MyResultFragmentDirections.actionMyResultFragmentToCommentFragment(id = filmListesi[position].uuid, bilgi = "eski")
            Navigation.findNavController(it).navigate(action)
        }
    }

}