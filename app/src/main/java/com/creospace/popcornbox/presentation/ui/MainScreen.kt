package com.creospace.popcornbox.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel(), paddingValues: PaddingValues) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.intentChannel.send(MainIntent.LoadNowPlayingMovies)
        }
    }

    state.nowPlayingMovies?.let { nowPlayingMovies ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(200.dp),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(nowPlayingMovies.size) { index ->
                val movie = nowPlayingMovies[index]
                AsyncImage(
                    model = movie.imageUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }
        }
    }

    if (state.isLoading) {
        Loading()
    }
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(50.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}
