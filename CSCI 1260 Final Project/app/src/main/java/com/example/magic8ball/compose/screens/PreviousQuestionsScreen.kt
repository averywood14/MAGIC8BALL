package com.example.magic8ball.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.magic8ball.compose.QandACard

// Composable function for the Previous Screen layout and functionality
// Used this source to create the bottom bar
// https://www.c-sharpcorner.com/article/material-3-bottom-navigation-bar-in-jetpack-compose/
@Composable
fun PreviousQuestionsScreen() {
    val viewModel : PreviousQuestionsViewModel = viewModel()
    val qAndAStack by viewModel.qAndAStack.collectAsState()
    val context = LocalContext.current
    val openDialog = remember{ mutableStateOf(false)}

    // On the launch of the page the user will see the current list of question and answers loaded from the file
    LaunchedEffect(Unit) {
       viewModel.loadQandAFromFile(context)
    }

    // A lazy column creates a scrollable list for the user: https://developer.android.com/develop/ui/compose/lists
    // Retrieves the Question and Answers from the stack
    LazyColumn {
        items(qAndAStack.size) {index ->
            // Same as using get with a stack
            val qAndA = qAndAStack[index]
            QandACard(qandA = qAndA, viewModel = viewModel, openDialog = openDialog)
        }
    }
}