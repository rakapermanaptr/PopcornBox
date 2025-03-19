package com.creospace.popcornbox.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creospace.popcornbox.data.mapper.toMovies
import com.creospace.popcornbox.data.remote.utils.Resource
import com.creospace.popcornbox.domain.model.Movie
import com.creospace.popcornbox.domain.repository.PopcornBoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NowPlayingState(
    val nowPlayingMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMsg: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PopcornBoxRepository
) : ViewModel() {

    val uiState: MutableStateFlow<NowPlayingState> = MutableStateFlow(NowPlayingState())

    init {
        getNowPlayingMovies()
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            repository.getNowPlayingMovies().collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        uiState.update { it.copy(isLoading = false, errorMsg = resource.message) }
                    }
                    Resource.Loading -> {
                        uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        uiState.update {
                            it.copy(isLoading = false, nowPlayingMovies = resource.data.results.toMovies())
                        }
                    }
                }
            }
        }
    }
}