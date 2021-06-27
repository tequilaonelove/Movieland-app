package com.testing.videoapp.data.models

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("display_title")
    val title: String,
    @SerializedName("multimedia")
    val multimedia: Multimedia,
    @SerializedName("opening_date")
    val openingDate: String,
    @SerializedName("publication_date")
    val publicationDate: String,
    @SerializedName("summary_short")
    val description: String
) {
    data class Multimedia(
        @SerializedName("src")
        val imageUrl: String
    )
}

