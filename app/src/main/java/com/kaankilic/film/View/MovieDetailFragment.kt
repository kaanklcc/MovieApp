package com.kaankilic.film.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.kaankilic.film.R
import com.kaankilic.film.databinding.FragmentMovieDetailBinding
import com.kaankilic.film.util.gorselIndir
import com.kaankilic.film.util.placeHolderYap
import com.kaankilic.film.viewModel.MovieDetailViewModel


class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MovieDetailViewModel
    var movieId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button2.setOnClickListener { yeniEkle(it) }
        binding.button2.setBackgroundResource(R.drawable.button)

        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        arguments?.let {
            movieId=MovieDetailFragmentArgs.fromBundle(it).movieID

        }
        viewModel.roomVerisiniAL(movieId)
        observeLiveData()

    }
    fun yeniEkle(view: View){
        val action = MovieDetailFragmentDirections.actionMovieDetailFragmentToCommentFragment(id=movieId, bilgi = "yeni")
        Navigation.findNavController(view).navigate(action)

    }
    private fun observeLiveData(){
        viewModel.movieLiveData.observe(viewLifecycleOwner){
            binding.movieName.text=it.originalTitle
            binding.movieYear.text=it.releaseDate
            binding.movieReview.text=it.overview
            binding.movieLanguage.text=it.originalLanguage

           // binding.movieImage.gorselIndir(it.posterPath, placeHolderYap(requireContext()))
            binding.movieImage.gorselIndir("https://image.tmdb.org/t/p/w500/${it.posterPath}", placeHolderYap(requireContext()))

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}