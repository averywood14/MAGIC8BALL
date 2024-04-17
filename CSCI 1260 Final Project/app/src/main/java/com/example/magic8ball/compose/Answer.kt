package com.example.magic8ball.compose

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// A composable function for the answer from the Magic 8 ball
// The text is modified within this function
@Composable
fun Answer(
    text : String,
    modifier: Modifier = Modifier
){
    // Formatting the text within the magic 8 ball
    Text(
        text = text,
        color = Color.White.copy(alpha = 0.4f),
        style = MaterialTheme.typography.labelSmall,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}