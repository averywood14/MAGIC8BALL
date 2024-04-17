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

@Composable
fun Magic8BallAnimation(
    modifier: Modifier = Modifier,
    isAsking: Boolean = false
){
    var currentRotation by remember { mutableStateOf(0f)}
    val rotation = remember { Animatable(currentRotation) }

    LaunchedEffect(isAsking) {
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
        }else {
            if(currentRotation > 0f){
                // Slows down the rotation
                rotation.animateTo(
                    targetValue = currentRotation + 50,
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
    Magic8Ball(
        modifier = modifier
            .padding(24.dp),
        rotationDegrees = rotation.value
    )
}