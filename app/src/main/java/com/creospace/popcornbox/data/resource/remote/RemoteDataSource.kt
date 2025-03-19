package com.creospace.popcornbox.data.resource.remote

import com.creospace.popcornbox.data.resource.remote.response.BaseResponseList
import com.creospace.popcornbox.data.resource.remote.response.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getNowPlayingMovies(): Response<BaseResponseList<MovieResponse>> {
        return apiService.getNowPlayingMovies()
    }
}