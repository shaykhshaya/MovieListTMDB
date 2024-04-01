package com.example.movieslistapp.data.remote

interface ConnectClient {

    suspend fun getPopularMovieList(callback: (PopularListResponse, String) -> Unit)

    suspend fun getMovieDetail(callback:(MovieDetailResponse, String) -> Unit)

    suspend fun getLatestMovie(callback: (MovieDto, String) -> Unit)

}