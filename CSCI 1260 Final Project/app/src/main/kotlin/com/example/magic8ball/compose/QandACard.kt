package com.example.magic8ball.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.magic8ball.compose.screens.PreviousQuestionsViewModel
import com.example.magic8ball.models.QandA
import org.w3c.dom.Text

// Composable function that creates a card to display the question and matching answer - along with a delete option
@Composable
fun QandACard(qandA: QandA, viewModel: PreviousQuestionsViewModel, openDialog: MutableState<Boolean>){
    val context = LocalContext.current

    // What is a card? : https://developer.android.com/develop/ui/compose/components/card
    Card(
        // Formatting the card
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ){
        Row(
            // Creating a row within the card and formatting it
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                // Creating a column within the card and formatting it
                modifier = Modifier
                    .padding(16.dp)
            ){
               Text(
                   // Showing the question to the user and formatting it
                   text = "Q: ${qandA.question}",
                   style = MaterialTheme.typography.bodyMedium,
                   color = Color.Black
               )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    // Showing the answer to the user and formatting it
                    text = "A: ${qandA.answer}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            // Creating an icon with a delete value
            // Sets the open dialog to true
            IconButton(onClick = { openDialog.value = true }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
    // Will call the ConfirmDeleteDialog and delete the question where the icon was clicked
    ConfirmDeleteDialog(item = qandA, viewModel = viewModel, context = context, openDialog = openDialog)
}


