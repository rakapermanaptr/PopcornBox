package com.creospace.popcornbox.presentation.ui

sealed class MainIntent {
    data object LoadNowPlayingMovies: MainIntent()
}