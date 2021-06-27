package com.testing.videoapp.data.repository

import com.testing.videoapp.Constants
import com.testing.videoapp.data.api.ReviewsApi

class ReviewsRepository(private val reviewsApi: ReviewsApi) {
    suspend fun getReviews(
        offset: Int? = null,
        orderBy: String? = null
    ) = reviewsApi.getReviews(
        apiKey = Constants.apiKey,
        offset = offset ?: 0,
        orderBy = orderBy ?: Constants.orderByOpeningDate
    )
}

fun provideReviewsRepository(reviewsApi: ReviewsApi): ReviewsRepository {
    return ReviewsRepository(reviewsApi)
}