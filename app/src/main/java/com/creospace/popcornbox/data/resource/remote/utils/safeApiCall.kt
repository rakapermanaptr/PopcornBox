package com.creospace.popcornbox.data.resource.remote.utils

import retrofit2.Response

// Safe API call handler with suspend
suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = apiCall()
        when {
            response.isSuccessful -> {
                response.body()?.let { Resource.Success(it) }
                    ?: Resource.Error("HTTP 200: Empty response body")
            }

            else -> Resource.Error("HTTP Error: ${response.code()} - ${response.message()}")
        }
    } catch (exception: Throwable) {
        handleApiError(exception)
    }
}