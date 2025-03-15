package com.creospace.popcornbox.data.mapper

import com.creospace.popcornbox.data.remote.response.BaseResponseList
import com.creospace.popcornbox.data.remote.response.MovieResponse
import com.creospace.popcornbox.domain.model.Movie

fun MovieResponse.toMovie() = Movie(
    id = id,
    title = title.orEmpty(),
    imageUrl = "https://image.tmdb.org/t/p/w500${posterPath}"
)

fun List<MovieResponse>.toMovies(): List<Movie> {
    val movies = arrayListOf<Movie>()
    this.map { movieList ->
        movies.add(movieList.toMovie())
    }
    return movies
}