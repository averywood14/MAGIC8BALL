package com.example.magic8ball.models

data class Response(
    val reading: String,
    val question: String?,
    val sentiment: Sentiment?
)

