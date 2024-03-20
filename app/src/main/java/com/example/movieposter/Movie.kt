package com.example.movieposter

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "Title")
    val title: String,
    @Json(name = "Year")
    val year: String,
    @Json(name = "Poster")
    val image: String,
)
