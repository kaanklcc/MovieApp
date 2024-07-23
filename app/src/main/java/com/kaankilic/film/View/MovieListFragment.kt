package com.kaankilic.film.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaankilic.film.adapter.MovieAdapter
import com.kaankilic.film.databinding.FragmentMovieListBinding
import com.kaankilic.film.viewModel.MovieListViewModel


class MovieListFragment : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieListViewModel
    private val movieRecyclerAdapter = MovieAdapter(arrayListOf())
    var movieId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button3.setOnClickListener { resultaDön(it) }

       /* arguments?.let {
            movieId = MovieListFragmentArgs.fromBundle(it).Id

        }*/


        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let { viewModel.searchMovies(it) }
                return true
            }
        })

        viewModel= ViewModelProvider(this)[MovieListViewModel::class.java]
        viewModel.refreshData()
        binding.movieRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.movieRecyclerView.adapter= movieRecyclerAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.movieHataMesaji.visibility=View.GONE
            binding.movieYukleniyor.visibility=View.VISIBLE
            binding.movieRecyclerView.visibility=View.GONE
            viewModel.refreshDataFromIntarnet()
            binding.swipeRefreshLayout.isRefreshing=false

        }
        observeLiveData()


    }
    fun resultaDön(view:View){
        val action = MovieListFragmentDirections.actionMovieListFragmentToMyResultFragment(movieId)
        Navigation.findNavController(view).navigate(action)
    }

    private fun observeLiveData(){

        viewModel.Movie.observe(viewLifecycleOwner){
            //recycler view gelecek
            //movieAdapter.movieListesiniGuncelle(it)
            movieRecyclerAdapter.movieListesiniGuncelle(it)
            binding.movieRecyclerView.visibility=View.VISIBLE
        }
        viewModel.movieLoading.observe(viewLifecycleOwner){
            if (it){
                binding.movieYukleniyor.visibility=View.VISIBLE
                binding.movieHataMesaji.visibility=View.GONE
                binding.movieRecyclerView.visibility=View.VISIBLE
            }else{
                binding.movieYukleniyor.visibility=View.GONE

            }

        }

        viewModel.movieError.observe(viewLifecycleOwner){
            if (it){
                binding.movieYukleniyor.visibility=View.GONE
                binding.movieHataMesaji.visibility=View.VISIBLE
                binding.movieRecyclerView.visibility=View.GONE

            }else{
                binding.movieHataMesaji.visibility=View.GONE

            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}