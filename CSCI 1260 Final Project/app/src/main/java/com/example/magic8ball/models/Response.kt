package com.example.magic8ball.models

// A response model that sets the values for response to a string,
// the question (from the user) to a nullable string,
// and the sentiment to a nullable sentiment
data class Response(
    val reading: String,
    val question: String?,
    val sentiment: Sentiment?
)

