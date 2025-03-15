package com.creospace.popcornbox.domain.repository

import com.creospace.popcornbox.data.remote.response.BaseResponseList
import com.creospace.popcornbox.data.remote.response.MovieResponse
import com.creospace.popcornbox.data.remote.utils.Resource
import com.creospace.popcornbox.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface PopcornBoxRepository {
    fun getNowPlayingMovies(): Flow<Resource<BaseResponseList<MovieResponse>>>
}