package com.example.movieslistapp.presentation.movie_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieslistapp.data.remote.MovieDto
import com.example.movieslistapp.data.remote.PopularListResponse
import com.example.movieslistapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _mLatestListSharedFlow = MutableSharedFlow<MovieDto>()
    val mLatestListSharedFlow = _mLatestListSharedFlow.asSharedFlow()

    private val _mPopularListSharedFlow = MutableSharedFlow<PopularListResponse>()
    val mPopularListSharedFlow = _mPopularListSharedFlow.asSharedFlow()

    fun getLatestMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getLatestMovie()
            response?.let { _mLatestListSharedFlow.emit(it) }
        }
    }

    fun getPopularMovieList() {
        viewModelScope.launch {
            val response = repository.getPopularMovieList()
            response?.let { _mPopularListSharedFlow.emit(it) }
        }
    }
}