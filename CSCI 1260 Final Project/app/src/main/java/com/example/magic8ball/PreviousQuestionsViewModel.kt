package com.example.magic8ball

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.magic8ball.models.QandA
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.Stack

class PreviousQuestionsViewModel : ViewModel() {
    fun loadQandAFromFile(context: Context, fileName: String): Stack<QandA> {
        val file = File(context.filesDir, fileName)
        val json = file.readText()
        val type = object : TypeToken<List<QandA>>() {}.type
        val list = Gson().fromJson<List<QandA>>(json, type)
        return Stack<QandA>().apply { addAll(list) }
    }
}