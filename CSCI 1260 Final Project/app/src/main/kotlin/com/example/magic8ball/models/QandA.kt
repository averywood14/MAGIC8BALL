package com.example.magic8ball.models

import java.util.UUID

// A QandA model that sets the question and answer values to an empty string
data class QandA (
    // Giving the question an ID to be able to uniquely identify (for deletion)
    val id: String = UUID.randomUUID().toString(),
    val question: String = "",
    val answer: String = ""
)
