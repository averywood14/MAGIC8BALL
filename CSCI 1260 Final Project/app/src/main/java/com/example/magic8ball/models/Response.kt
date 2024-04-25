package com.example.magic8ball.models

// A response model that sets the values for response to a string,
// the question (from the user) to a nullable string,
// and the sentiment to a nullable sentiment
// The Response from the API is returned as a JSON (aka a dictionary)
// https://www.usna.edu/Users/cs/nchamber/courses/forall/s20/lec/l25/#:~:text=JSON%20at%20its%20top%2Dlevel,just%204%20attribute%2Fvalue%20pairs
data class Response(
    val reading: String,
    val question: String?,
    val sentiment: Sentiment?
)

