package com.creospace.popcornbox.data.repository

import com.creospace.popcornbox.data.remote.ApiService
import com.creospace.popcornbox.data.remote.response.BaseResponseList
import com.creospace.popcornbox.data.remote.response.MovieResponse
import com.creospace.popcornbox.data.remote.utils.Resource
import com.creospace.popcornbox.data.remote.utils.handleApiError
import com.creospace.popcornbox.data.remote.utils.safeApiCall
import com.creospace.popcornbox.domain.repository.PopcornBoxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PopcornBoxRepositoryImpl @Inject constructor(private val apiService: ApiService) : PopcornBoxRepository {

    override fun getNowPlayingMovies(): Flow<Resource<BaseResponseList<MovieResponse>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall { apiService.getNowPlayingMovies() }
            emit(result)
        }.catch { exception ->
            emit(handleApiError(exception))
        }
}