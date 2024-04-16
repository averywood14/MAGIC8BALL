package com.example.magic8ball

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.magic8ball.api.EightBallAPIInstance
import kotlinx.coroutines.launch
import kotlin.random.Random

data class ResponseModel(val question: String = "", val response: String = "")
class MainActivityViewModel : ViewModel() {
    var responseModel by mutableStateOf(ResponseModel())
    fun askQuestion() {
        viewModelScope.launch {
            val response = EightBallAPIInstance.api.askQuestion()
            // Do something with response
            responseModel = responseModel.copy(response = response.reading)
        }
    }

    fun askBiasedQuestion(question: String) {
        viewModelScope.launch {
            val response = EightBallAPIInstance.api.askBiasedQuestion(question, Random.nextBoolean())
            responseModel = responseModel.copy(response = response.reading)
        }
    }

    fun questionAsked(question: String){
        responseModel = responseModel.copy(question = question)
    }
}