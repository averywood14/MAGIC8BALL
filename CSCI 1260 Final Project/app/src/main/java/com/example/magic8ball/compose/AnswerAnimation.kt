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

@Composable
fun AnswerAnimation(
    modifier: Modifier = Modifier,
    isAsking : Boolean = false,
    text: String = ""
){
    var currentOpacity by remember { mutableFloatStateOf(1.0f)}
    val opacity = remember { Animatable(currentOpacity)}

    LaunchedEffect(isAsking) {
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
    Answer(text = text,
        modifier = modifier
            .alpha(currentOpacity)
    )
}