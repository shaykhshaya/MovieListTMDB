package com.example.movieslistapp.domain.repository

import com.example.movieslistapp.data.remote.MovieDetailResponse
import com.example.movieslistapp.data.remote.MovieDto
import com.example.movieslistapp.data.remote.PopularListResponse

interface MovieRepository {

    suspend fun getPopularMovieList(): PopularListResponse?

    suspend fun getMovieDetail(): MovieDetailResponse?

    suspend fun getLatestMovie(): MovieDto?

}