package com.example.movieslistapp.presentation.movie_listing

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieslistapp.data.remote.MovieDto
import com.example.movieslistapp.databinding.FragmentLatestMoviesBinding
import com.example.movieslistapp.presentation.movie_detail.MovieDetailActivity
import com.example.movieslistapp.presentation.movie_listing.adapter.MovieListAdapter
import com.example.movieslistapp.util.BuildNetworkClient
import com.example.movieslistapp.util.Constants
import com.example.movieslistapp.util.GridItemDecorator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class LatestMoviesFragment : Fragment() {

    private lateinit var binding: FragmentLatestMoviesBinding
    private val viewModel: MovieListViewModel by activityViewModels()
    private var mMovieListAdapter: MovieListAdapter? = null
    private var mLatestMovie = arrayListOf<MovieDto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLatestMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeMovieListAdapter()
        hitLatestMovieApi("1")
        observeLatestMovie()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeLatestMovie() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mLatestListSharedFlow.collectLatest { response ->
                    if (response != null) {
                        mLatestMovie.clear()
                        mLatestMovie.add(response)
                        mMovieListAdapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun initializeMovieListAdapter() {
        mMovieListAdapter = MovieListAdapter(mLatestMovie) { movieId ->
            movieId?.let {
                MovieDetailActivity.start(requireContext(), it)
            }
        }
        binding.rvMovieList.apply {
            adapter = mMovieListAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(GridItemDecorator(2, 40, false))
        }
    }

    private fun hitLatestMovieApi(page: String) {
        BuildNetworkClient.build(
            page = page,
            endPoint = Constants.END_POINT_LATEST,
            path = ""
        )
        viewModel.getLatestMovie()
    }
}