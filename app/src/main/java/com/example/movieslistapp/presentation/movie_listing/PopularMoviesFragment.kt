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
import com.example.movieslistapp.databinding.FragmentPopularMoviesBinding
import com.example.movieslistapp.presentation.movie_detail.MovieDetailActivity
import com.example.movieslistapp.presentation.movie_listing.adapter.MovieListAdapter
import com.example.movieslistapp.util.BuildNetworkClient
import com.example.movieslistapp.util.Constants
import com.example.movieslistapp.util.GridItemDecorator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PopularMoviesFragment : Fragment() {

    private lateinit var binding: FragmentPopularMoviesBinding
    private val viewModel: MovieListViewModel by activityViewModels()
    private var mMovieListAdapter: MovieListAdapter? = null
    private var mMovieList = arrayListOf<MovieDto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeMovieListAdapter()
        hitPopularMovieListApi("1")
        observePopularMovieList()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observePopularMovieList() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mPopularListSharedFlow.collectLatest { response ->
                    if (response != null) {
                        val movieList = response.results
                        mMovieList.clear()
                        if (movieList != null) {
                            mMovieList.addAll(movieList)
                        }
                        mMovieListAdapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun initializeMovieListAdapter() {
        mMovieListAdapter = MovieListAdapter(mMovieList) { movieId ->
            movieId?.let {
                MovieDetailActivity.start(requireContext(), it)
            }
        }
        binding.rvMovieList.apply {
            adapter = mMovieListAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(GridItemDecorator(2, 5, false))
        }
    }

    private fun hitPopularMovieListApi(page: String) {
        BuildNetworkClient.build(
            page = page,
            endPoint = Constants.END_POINT_POPULAR_LIST,
            path = ""
        )
        viewModel.getPopularMovieList()
    }
}