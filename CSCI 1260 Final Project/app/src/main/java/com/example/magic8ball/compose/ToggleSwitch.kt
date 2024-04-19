package com.example.magic8ball.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.magic8ball.MainActivityViewModel

// Composable function that creates a toggle switch next to the text box,
// where the user asks their question, that can be switched on and off.
// Depending on if the user is feeling lucky with their question.
@Composable
fun ToggleSwitch(
    // Passing in the entire view model
    viewModel: MainActivityViewModel
) {
    // Formatting the toggle switch
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Switch(checked = viewModel.feelingLucky, onCheckedChange = {viewModel.feelingLucky = it})
        Text(text = "Feeling Lucky?", textAlign = TextAlign.Center, color = Color.Black,
            modifier = Modifier
                .size(64.dp)
        )
    }
}