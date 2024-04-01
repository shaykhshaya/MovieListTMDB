package com.example.movieslistapp.data.repository

import com.example.movieslistapp.data.remote.ConnectClient
import com.example.movieslistapp.data.remote.MovieDetailResponse
import com.example.movieslistapp.data.remote.MovieDto
import com.example.movieslistapp.data.remote.PopularListResponse
import com.example.movieslistapp.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val client: ConnectClient
) : MovieRepository {


    override suspend fun getPopularMovieList(): PopularListResponse? {
        var response: PopularListResponse? = null
        client.getPopularMovieList { popularListResponse, s ->
            response = popularListResponse
        }
        return response
    }

    override suspend fun getMovieDetail(): MovieDetailResponse? {
        var response: MovieDetailResponse? = null
        client.getMovieDetail { data, s ->
            response = data
        }
        return response
    }

    override suspend fun getLatestMovie(): MovieDto? {
        var response: MovieDto? = null
        client.getLatestMovie { data, s ->
            response = data
        }
        return response
    }


}