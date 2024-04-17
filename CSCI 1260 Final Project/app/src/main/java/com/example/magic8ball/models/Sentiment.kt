package com.example.magic8ball.models

// A sentiment model that hold calculation for the API to analyze the sentiment
// (ie positive or negative) for a biased question
data class Sentiment(
    val score: Int,
    val comparative: Double,
    val calculation: List<Calculation>,
    val tokens: List<String>,
    val words: List<String>,
    val positive: List<String>,
    val negative: List<String>
)
