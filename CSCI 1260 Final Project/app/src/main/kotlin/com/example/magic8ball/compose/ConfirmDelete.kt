package com.example.magic8ball.compose

import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.magic8ball.compose.screens.PreviousQuestionsViewModel
import com.example.magic8ball.models.QandA

// Composable function that creates a confirmation pop up
// When the user is attempting to delete a question from the list
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDeleteDialog(
    item: QandA,
    viewModel: PreviousQuestionsViewModel,
    context: Context,
    openDialog: MutableState<Boolean>
){
    // If the open dialog value is true the pop up shows to the user
    if(openDialog.value){

        AlertDialog(
            // When the user clicks outside of the question the open dialog will not appear
            onDismissRequest = {
                openDialog.value = false
                               },
            // The title of the dialog
            title = {
                    Text("Confirm Delete")
            },
            // The message shown in the dialog
            text = {
                   Text("Are you sure you want to delete this item?")
            },
            // Creating a confirmation button, when clicked will call the deleteQandA and delete the question and response
            confirmButton = {
                Button(onClick = {
                    viewModel.deleteQandA(context, item)
                    openDialog.value = false
                }) {
                    Text("Confirm")
                }
            },
            // Creating a dismiss button that when clicked will not delete the question and response
            dismissButton = {
                Button(onClick = {
                    openDialog.value = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}