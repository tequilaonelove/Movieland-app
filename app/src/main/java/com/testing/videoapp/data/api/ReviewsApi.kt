package com.testing.videoapp.data.api

import com.testing.videoapp.data.models.ReviewModel
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface ReviewsApi {
    @GET("/svc/movies/v2/reviews/all.json")
    suspend fun getReviews(
        @Query("api-key") apiKey: String,
        @Query("offset") offset: Int,
        @Query("orderBy") orderBy: String
    ): ReviewModel
}

fun provideReviewsApi(retrofit: Retrofit): ReviewsApi {
    return retrofit.create(ReviewsApi::class.java)
}