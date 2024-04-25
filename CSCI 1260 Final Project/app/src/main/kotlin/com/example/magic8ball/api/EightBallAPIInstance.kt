package com.example.magic8ball.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// This creates an API Instance object that uses the API from https://eightballapi.com/api/
// It uses Retrofit to setup the API with the BASE_URL and
// sets up the JSON (Dictionary) conversion using the GSON library
object  EightBallAPIInstance {
    private const val BASE_URL = "https://eightballapi.com/api/"

    val api: EightBallApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EightBallApi::class.java)
    }
}