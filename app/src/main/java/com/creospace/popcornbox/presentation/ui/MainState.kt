package com.creospace.popcornbox.presentation.ui

import com.creospace.popcornbox.domain.model.Movie

data class MainState(
    val isLoading: Boolean = false,
    val nowPlayingMovies: List<Movie>? = null,
    val errorMessage: String? = null
)