package com.example.magic8ball.compose

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.magic8ball.MainActivityViewModel

// Composable function that creates a text box for the user to ask their question,
// and saves it to the view model.
// Hides the keyboard once the user is done asking their question
@Composable
fun UserQuestion(
    // Passing in the entire view model
    viewModel: MainActivityViewModel,
    onDoneAction: () -> Unit
){
    val focusManager = LocalFocusManager.current

    // Laying out whats inside the row
    TextField(value = viewModel.responseModel.question,
        onValueChange = {viewModel.questionAsked(it)},
        label = { Text(text = "Ask me a Question") },
        singleLine = true,
        // Hiding the keyboard on done
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            onDoneAction()
        })
    )
}