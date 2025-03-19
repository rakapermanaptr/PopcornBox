package com.creospace.popcornbox.domain.repository

import com.creospace.popcornbox.data.resource.remote.response.BaseResponseList
import com.creospace.popcornbox.data.resource.remote.response.MovieResponse
import com.creospace.popcornbox.data.resource.remote.utils.Resource
import com.creospace.popcornbox.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface PopcornBoxRepository {
    fun getNowPlayingMovies(): Flow<Resource<BaseResponseList<MovieResponse>>>
}