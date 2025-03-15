package com.creospace.popcornbox.data.remote.utils

import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

// Exception handling with detailed Resource.Error
fun <T> handleApiError(exception: Throwable): Resource<T> {
    val message = when (exception) {
        is TimeoutException -> "Request timed out. Please try again."
        is IOException -> "Network error. Please check your connection."
        is HttpException -> {
            when (val statusCode = exception.code()) {
                400 -> "Bad Request"
                401 -> "Unauthorized. Please check your credentials."
                403 -> "Forbidden. Access is denied."
                404 -> "Resource not found."
                500 -> "Internal Server Error. Please try again later."
                503 -> "Service Unavailable. Please try again later."
                else -> "Unexpected HTTP Error: $statusCode"
            }
        }

        is JsonParseException, is MalformedJsonException -> "Malformed JSON received. Parsing failed."
        is IllegalArgumentException -> "Invalid argument provided. ${exception.message}"
        is IllegalStateException -> "Illegal application state. ${exception.message}"
        else -> "Unexpected error occurred: ${exception.message}"
    }
    return Resource.Error(message, exception)
}