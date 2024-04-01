package com.example.movieslistapp.data.remote

import com.example.request_get.NetworkCall
import javax.inject.Inject

class ConnectClientImpl @Inject constructor() : ConnectClient {

    override suspend fun getPopularMovieList(callback: (PopularListResponse, String) -> Unit) {
        val popularMovieListResponse = NetworkCall<PopularListResponse>(
            responseType = PopularListResponse::class.java
        ) { data, message ->
            data?.let { callback.invoke(it, message) }
        }
        popularMovieListResponse.get()
    }

    override suspend fun getMovieDetail(callback: (MovieDetailResponse, String) -> Unit) {
        val movieDetailResponse = NetworkCall<MovieDetailResponse>(
            responseType = MovieDetailResponse::class.java
        ) { data, message ->
            data?.let { callback.invoke(it, message) }
        }
        movieDetailResponse.get()
    }

    override suspend fun getLatestMovie(callback: (MovieDto, String) -> Unit) {
        val latestMovieListResponse = NetworkCall<MovieDto>(
            responseType = MovieDto::class.java
        ) { data, message ->
            data?.let { callback.invoke(it, message) }
        }
        latestMovieListResponse.get()
    }
}