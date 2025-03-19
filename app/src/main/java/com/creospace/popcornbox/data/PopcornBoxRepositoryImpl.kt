package com.creospace.popcornbox.data

import com.creospace.popcornbox.data.resource.remote.ApiService
import com.creospace.popcornbox.data.resource.remote.RemoteDataSource
import com.creospace.popcornbox.data.resource.remote.response.BaseResponseList
import com.creospace.popcornbox.data.resource.remote.response.MovieResponse
import com.creospace.popcornbox.data.resource.remote.utils.Resource
import com.creospace.popcornbox.data.resource.remote.utils.handleApiError
import com.creospace.popcornbox.data.resource.remote.utils.safeApiCall
import com.creospace.popcornbox.domain.repository.PopcornBoxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PopcornBoxRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PopcornBoxRepository {

    override fun getNowPlayingMovies(): Flow<Resource<BaseResponseList<MovieResponse>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall { remoteDataSource.getNowPlayingMovies() }
            emit(result)
        }.catch { exception ->
            emit(handleApiError(exception))
        }
}