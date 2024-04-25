package com.example.magic8ball.compose.screens

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.magic8ball.Constants
import com.example.magic8ball.models.QandA
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import java.util.Stack

// A class that extends the current view model. Manages a stack of Question and answers,
// and loads them into a JSON file. Also Provides a way to delete a question
class PreviousQuestionsViewModel : ViewModel() {
    // Creating variables: What is Mutable State Flow?: https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-mutable-state-flow/#:~:text=A%20mutable%20StateFlow%20that%20provides,for%20details%20on%20state%20flows.
    private val _qAndAStack = MutableStateFlow(Stack<QandA>())
    val qAndAStack: StateFlow<Stack<QandA>> = _qAndAStack

    // Function that loads a list of questions and answers from the stored JSON file
    // If the file cannot be read it returns and exception
    // If the reading is successful the list of questions and answers is applied to the stack
    fun loadQandAFromFile(context: Context) {
        val file = File(context.filesDir, Constants.fileName)
        val json = try {
            file.readText()
        } catch(e: Exception){
            Log.d("JSONReadError","Error reading json File ${e.localizedMessage}")
            return
        }
        val type = object : TypeToken<List<QandA>>() {}.type
        val list = Gson().fromJson<List<QandA>>(json, type)
        _qAndAStack.value = Stack<QandA>().apply { addAll(list) }
    }

    // Function that created a new stack by filtering out the item with the given id
    // The new stack is set to the _qAndAStack value- the new stack is written to the JSON file
    // Function handles errors if the JSON file cannot be written.
    fun deleteQandA(context: Context, item: QandA) {

        Log.d("PreviousQuestionsViewModel", "Deleting item with id: ${item.id}")

        val newStack = _qAndAStack.value.filterNot { it.id == item.id }.toCollection(Stack())

        newStack.forEach{ Log.d("PreviousQuestionsViewModel", "Item in new stack wiht id ${it.id}")}

        _qAndAStack.value = newStack

        val json = Gson().toJson(newStack)

        try {
            val file = File(context.filesDir, Constants.fileName)
            file.writeText(json)
        } catch( e: Exception){
            Log.d("FileWriteError", "Error Wrtiting JSON file: ${e.localizedMessage}")
            return
        }

        loadQandAFromFile(context)
    }
}
