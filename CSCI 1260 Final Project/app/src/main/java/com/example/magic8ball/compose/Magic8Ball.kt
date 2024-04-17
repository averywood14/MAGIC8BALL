package com.example.magic8ball.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.magic8ball.R

// Composable function to create the Magic 8 ball image,
// the sizing is set within this function
@Composable
fun Magic8Ball(
   modifier: Modifier = Modifier,
   rotationDegrees : Float = 0f
){
    // Formatting the Magic 8 ball
    Image(
        // Making the image of the magic 8 ball appear
        painter = painterResource(id = R.drawable.magicball),
        contentDescription = null,
        modifier = modifier
            // Fixing the sizing of the magic 8 ball
            .fillMaxSize()
            .padding(16.dp)
            .rotate(rotationDegrees)

    )
}

