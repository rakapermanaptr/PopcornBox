package com.creospace.popcornbox.data.resource.remote

import com.creospace.popcornbox.data.resource.remote.response.BaseResponseList
import com.creospace.popcornbox.data.resource.remote.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<BaseResponseList<MovieResponse>>
}