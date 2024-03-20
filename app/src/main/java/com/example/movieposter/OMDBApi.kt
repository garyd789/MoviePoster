package com.example.movieposter

import retrofit2.http.GET
import retrofit2.http.Query



interface OMDBApi {
    @GET("/")
    suspend fun fetchPoster(
        @Query("apikey") apikey: String,
        @Query("t") title: String
    ): Movie
}