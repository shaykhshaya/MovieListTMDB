package com.example.movieslistapp.di

import com.example.movieslistapp.data.remote.ConnectClient
import com.example.movieslistapp.data.repository.MovieRepositoryImpl
import com.example.movieslistapp.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        client: ConnectClient
    ): MovieRepository {
        return MovieRepositoryImpl(client)
    }
}