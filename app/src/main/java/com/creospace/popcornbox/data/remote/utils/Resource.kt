package com.creospace.popcornbox.data.remote.utils

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()

    data class Success<T>(val data: T) : Resource<T>()

    data class Error(
        val message: String,
        val exception: Throwable? = null
    ) : Resource<Nothing>()
}