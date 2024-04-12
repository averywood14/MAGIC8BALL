package com.example.magic8ball.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EightBallAPIInstance {
    private const val BASE_URL = "https://eightballapi.com/api/"

    val api: EightBallApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EightBallApi::class.java)
    }
}