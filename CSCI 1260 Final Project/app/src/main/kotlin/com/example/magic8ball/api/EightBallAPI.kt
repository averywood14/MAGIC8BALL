package com.example.magic8ball.api

import com.example.magic8ball.models.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Using Retrofit to create an API definition to call for the API endpoints.
interface EightBallApi {
    @GET(".")
    suspend fun askQuestion(): Response

    @GET("biased")
    suspend fun askBiasedQuestion(@Query("question") question: String, @Query("lucky") feelingLucky: Boolean): Response
}