package com.creospace.popcornbox.data.di

import com.creospace.popcornbox.data.PopcornBoxRepositoryImpl
import com.creospace.popcornbox.domain.repository.PopcornBoxRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindRepository(
        popcornBoxRepositoryImpl: PopcornBoxRepositoryImpl
    ): PopcornBoxRepository

}