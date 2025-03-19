package com.creospace.popcornbox.data.di

import com.creospace.popcornbox.data.resource.remote.ApiService
import com.creospace.popcornbox.data.resource.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(@Singleton apiService: ApiService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }
}