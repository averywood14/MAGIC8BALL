package com.example.magic8ball

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.magic8ball.api.EightBallAPIInstance
import com.example.magic8ball.models.QandA
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import kotlin.random.Random

// View model that creates function for every action on the main page.
// Creates functions for the question the user is asking and returning the response
// Creates function to update the JSON file with hte question the user just asked
class MainActivityViewModel : ViewModel() {
    var responseModel by mutableStateOf(QandA())
    var feelingLucky by mutableStateOf(false)
    val isAsking = MutableStateFlow(false)

    // Function that asks a question through the API
    // Updates  the response model
    // Saves the question and answer to the file
    fun askQuestion(context: Context) {
        viewModelScope.launch {
            responseModel = responseModel.copy(answer = "")
            isAsking.emit(true)
            val response = EightBallAPIInstance.api.askQuestion()
            // Do something with response
            delay(5000)
            responseModel = QandA(question = responseModel.question, answer = response.reading)
            isAsking.emit(false)
            saveQandAToFile(context, responseModel)
        }
    }

    // Function that asks a biased question to the API- with the toggle switch on
    // Updates the response model
    // Saves the question and answer to the file
    fun askBiasedQuestion(context: Context, question: String) {
        viewModelScope.launch {
            responseModel = responseModel.copy(answer = "")
            isAsking.emit(true)
            val response = EightBallAPIInstance.api.askBiasedQuestion(question, Random.nextBoolean())
            delay(5000)
            responseModel = QandA(question = responseModel.question, answer = response.reading)
            isAsking.emit(false)
            saveQandAToFile(context, responseModel)
        }
    }
    // This updates the response model for the question when the user types their question into the text box
    fun questionAsked(question: String){
        responseModel = responseModel.copy(question = question)
    }

    // Saves QandA object to the files directory
    // Loads existing QandA's from the file and handles errors
    // Appends the new QandA to the current list and updates the file
    fun saveQandAToFile(context: Context, qandA: QandA){
        val file = File(context.filesDir, Constants.fileName)
        val gson = Gson()
        val type = object : TypeToken<List<QandA>>() {}.type

        // Load the existing QandA form the file
        val existingQandAs = try{
            if(file.exists()){
                val json = file.readText()
                gson.fromJson<List<QandA>>(json,type)
            }else{
                emptyList()
            }
        }catch (e: Exception){
            emptyList<QandA>()
        }

        val updatedQandAs = existingQandAs + qandA

        val updatedJson = gson.toJson(updatedQandAs)

        file.writeText(updatedJson)
    }
}