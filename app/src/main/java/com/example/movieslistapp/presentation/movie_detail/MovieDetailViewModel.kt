package com.example.movieslistapp.presentation.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieslistapp.data.remote.MovieDetailResponse
import com.example.movieslistapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _mMovieDetailsSharedFlow = MutableSharedFlow<MovieDetailResponse>()
    val mMovieDetailsSharedFlow = _mMovieDetailsSharedFlow.asSharedFlow()

    fun getMovieDetails() {
        viewModelScope.launch {
            val response = repository.getMovieDetail()
            response?.let { _mMovieDetailsSharedFlow.emit(it) }
        }
    }
}