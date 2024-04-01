package com.example.movieslistapp.di

import com.example.movieslistapp.data.remote.ConnectClient
import com.example.movieslistapp.data.remote.ConnectClientImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

interface ClientModule {

    @Binds
    fun provideConnectClient(
        connectClientImpl: ConnectClientImpl
    ): ConnectClient

}

