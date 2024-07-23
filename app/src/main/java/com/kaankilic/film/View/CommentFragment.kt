package com.kaankilic.film.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

import com.kaankilic.film.databinding.FragmentCommentBinding
import com.kaankilic.film.model.Movie
import com.kaankilic.film.util.gorselIndir
import com.kaankilic.film.util.placeHolderYap
import com.kaankilic.film.viewModel.CommentFragmentViewModel



class CommentFragment : Fragment() {
    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : CommentFragmentViewModel
    var movieId = 0
    var bilgi = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CommentFragmentViewModel::class.java]


        binding.saveButton.setOnClickListener {  save(it)}
        binding.deleteButton.setOnClickListener {  delete(it)}

        arguments?.let {
            movieId = CommentFragmentArgs.fromBundle(it).id
            bilgi = CommentFragmentArgs.fromBundle(it).bilgi



            }

        viewModel.roomVerisiAl(movieId)
        observeLiveData()

        }
    private fun observeLiveData() {
        viewModel.movieLive.observe(viewLifecycleOwner) { movie ->
            movie?.let {
                binding.deleteButton.isEnabled = true
                binding.movieName.text = it.originalTitle
                binding.movieFoto.gorselIndir("https://image.tmdb.org/t/p/w500/${it.posterPath}", placeHolderYap(requireContext()))
                binding.commentText.setText(it.overview) // Eğer edittext'e kaydedilen texti burada set ediyorsanız
                if (bilgi.equals("yeni")){
                    binding.commentText.setText("")
                }

            }
        }
    }
    fun save(view:View){
        viewModel.movieLive.value?.let {movieLiveData ->
            val movie = Movie(
                id = movieId.toString(),
                originalTitle = binding.movieName.text.toString(),
                originalLanguage = "en", // Gerekirse uygun değeri ayarlayın
                overview = binding.commentText.text.toString(), // Gerekirse uygun değeri ayarlayın
                posterPath = "https://image.tmdb.org/t/p/w500/${movieLiveData.posterPath}",
                releaseDate = "release_date_value" // Gerekirse uygun değeri ayarlayın
            )
            viewModel.saveMovie(movie)
            val action = CommentFragmentDirections.actionCommentFragmentToMyResultFragment(ID =movieId )
            Navigation.findNavController(view).navigate(action)
        }
    }

    fun delete(view:View){
        viewModel.movieLive.value?.let {
            viewModel.deleteMovie(it)
        }
        val action = CommentFragmentDirections.actionCommentFragmentToMyResultFragment(ID = movieId)
        Navigation.findNavController(view).navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
