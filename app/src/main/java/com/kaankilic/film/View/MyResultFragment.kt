package com.kaankilic.film.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.kaankilic.film.R
import com.kaankilic.film.RoomDB.MovieDao
import com.kaankilic.film.RoomDB.MovieDatabase
import com.kaankilic.film.adapter.FilmAdapter
import com.kaankilic.film.adapter.MovieAdapter
import com.kaankilic.film.databinding.FragmentCommentBinding
import com.kaankilic.film.databinding.FragmentMyResultBinding
import com.kaankilic.film.model.Movie
import com.kaankilic.film.viewModel.ResultFragmentViewModel
import java.util.ArrayList


class MyResultFragment : Fragment() {
    private var _binding: FragmentMyResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var db : MovieDatabase
    private lateinit var movieDao : MovieDao
    private val filmRecyclerAdapter = FilmAdapter(arrayListOf())
    private lateinit var viewModel : ResultFragmentViewModel
    var filmId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(requireContext(),MovieDatabase::class.java,"FoodDatabase").build()
        movieDao = db.movieDao()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyResultBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ResultFragmentViewModel::class.java]
        binding.button.setOnClickListener {  anaDon(it)}

        arguments?.let {
            filmId= MyResultFragmentArgs.fromBundle(it).ID

        }

        binding.reviewRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.reviewRecyclerView.adapter=filmRecyclerAdapter
        viewModel.roomVerisiAl(filmId)
        observeLiveData()
    }
    fun verileriAl(){


    }
    fun anaDon(view: View){
        val action = MyResultFragmentDirections.actionMyResultFragmentToMovieListFragment()
        Navigation.findNavController(view).navigate(action)

    }

    fun observeLiveData(){
        viewModel.filmmutable.observe(viewLifecycleOwner){ movies->
            movies?.let {
                filmRecyclerAdapter.updateMovieList(it)
                binding.reviewRecyclerView.visibility = View.VISIBLE


            }


        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}