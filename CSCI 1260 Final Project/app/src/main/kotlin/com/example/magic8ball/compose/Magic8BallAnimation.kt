package com.example.magic8ball.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.nio.file.WatchEvent

// A Composable function that allows the Magic 8 Ball image to spin/animate,
// depending on if the user is asking a question or not
// Used this source to help with animation:
// https://nascimpact.medium.com/jetpack-compose-working-with-rotation-animation-aeddc5899b28
@Composable
fun Magic8BallAnimation(
    modifier: Modifier = Modifier,
    isAsking: Boolean = false
){
    // Variable that holds the current rotation angle
    var currentRotation by remember { mutableStateOf(0f)}
    // Animates the magic 8 ball - set to current rotation angle
    val rotation = remember { Animatable(currentRotation) }

    LaunchedEffect(isAsking) {
        // If the user is asking the ball rotates infinitely
        if(isAsking) {
            // Rotates infinitely while the user is asking
            rotation.animateTo(
                targetValue = currentRotation + 360f,
                animationSpec = infiniteRepeatable(
                    animation =  tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ){
                currentRotation = value
            }
        // If the user is not currently asking the animation is slowed and stopped
        }else {
            if(currentRotation > 0f){
                // Slows down the rotation
                rotation.animateTo(
                    targetValue = 360f,
                    animationSpec =  tween(
                        durationMillis =  1250,
                        easing = LinearOutSlowInEasing
                    )
                ){
                    currentRotation = value
                }
            }
        }
    }
    // Calls the Magic 8 Ball function and sets the rotationDegrees to the rotation value
    Magic8Ball(
        modifier = modifier
            .padding(24.dp),
        rotationDegrees = rotation.value
    )
}