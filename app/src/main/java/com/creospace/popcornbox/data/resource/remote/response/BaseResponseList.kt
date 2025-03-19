package com.creospace.popcornbox.data.resource.remote.response

import com.google.gson.annotations.SerializedName

data class BaseResponseList<T>(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<T> = emptyList(),
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
)