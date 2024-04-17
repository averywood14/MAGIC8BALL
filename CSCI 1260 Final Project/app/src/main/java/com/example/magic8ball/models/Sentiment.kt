package com.example.magic8ball.models

data class Sentiment(
    val score: Int,
    val comparative: Double,
    val calculation: List<Calculation>,
    val tokens: List<String>,
    val words: List<String>,
    val positive: List<String>,
    val negative: List<String>
)
