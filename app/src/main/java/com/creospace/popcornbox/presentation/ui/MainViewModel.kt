package com.creospace.popcornbox.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creospace.popcornbox.data.mapper.toMovies
import com.creospace.popcornbox.data.remote.utils.Resource
import com.creospace.popcornbox.domain.repository.PopcornBoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: PopcornBoxRepository) : ViewModel() {

    val intentChannel = Channel<MainIntent>(Channel.UNLIMITED)

    // state
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    MainIntent.LoadNowPlayingMovies -> getMovies()
                }
            }
        }
    }

    private fun getMovies() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }

        repository.getNowPlayingMovies().collect { resource ->
            _state.update { currentState ->
                when (resource) {
                    is Resource.Error -> currentState.copy(isLoading = false, errorMessage = resource.message)
                    Resource.Loading -> currentState.copy(isLoading = true)
                    is Resource.Success -> currentState.copy(isLoading = false, nowPlayingMovies = resource.data.results.toMovies())
                }
            }
        }
    }
}