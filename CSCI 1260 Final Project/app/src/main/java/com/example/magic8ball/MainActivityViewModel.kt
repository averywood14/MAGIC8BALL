package com.example.magic8ball

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.magic8ball.api.EightBallAPIInstance
import com.example.magic8ball.models.QandA
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivityViewModel : ViewModel() {
    var responseModel by mutableStateOf(QandA())
    var feelingLucky by mutableStateOf(false)
    val isAsking = MutableStateFlow(false)
    fun askQuestion() {
        viewModelScope.launch {
            responseModel = responseModel.copy(answer = "")
            isAsking.emit(true)
            val response = EightBallAPIInstance.api.askQuestion()
            // Do something with response
            delay(5000)
            responseModel = responseModel.copy(answer = response.reading)
            isAsking.emit(false)
        }
    }

    fun askBiasedQuestion(question: String) {
        viewModelScope.launch {
            responseModel = responseModel.copy(answer = "")
            isAsking.emit(true)
            val response = EightBallAPIInstance.api.askBiasedQuestion(question, Random.nextBoolean())
            delay(5000)
            responseModel = responseModel.copy(answer = response.reading)
            isAsking.emit(false)
        }
    }
    fun questionAsked(question: String){
        responseModel = responseModel.copy(question = question)
    }
}