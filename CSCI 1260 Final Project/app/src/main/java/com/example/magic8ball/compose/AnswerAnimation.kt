package com.example.magic8ball.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

// A composable function and animates the response within the magic 8 ball to fade in and out, while altering opacity
@Composable
fun AnswerAnimation(
    modifier: Modifier = Modifier,
    isAsking : Boolean = false,
    text: String = ""
){
    var currentOpacity by remember { mutableFloatStateOf(1.0f)}
    val opacity = remember { Animatable(currentOpacity)}

    LaunchedEffect(isAsking) {
        // if the isAsking boolean is true the response will ease in and out, altering the opacity
        if(isAsking){
            opacity.animateTo(
                targetValue = 0.0f,
                animationSpec = tween(
                    durationMillis = 125,
                    easing = EaseInOut
                )
            ){
                currentOpacity = value
            }
            // If the isAsking boolean is false the response is set to full opacity; creating a slower fade in effect
        } else {
            opacity.animateTo(
                targetValue = 1.0f,
                animationSpec =  tween(
                    durationMillis = 6000,
                    easing = EaseInOut
                )
            ){
                currentOpacity = value
            }
        }
    }
    // This applied the current opacity of the text to the answer
    Answer(text = text,
        modifier = modifier
            .alpha(currentOpacity)
    )
}