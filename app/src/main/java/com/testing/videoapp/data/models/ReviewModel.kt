package com.testing.videoapp.data.models


import com.google.gson.annotations.SerializedName

data class ReviewModel(
    @SerializedName("status")
    val status: String,
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("num_results")
    val numResults: Int,
    @SerializedName("results")
    val results: List<Review>
)