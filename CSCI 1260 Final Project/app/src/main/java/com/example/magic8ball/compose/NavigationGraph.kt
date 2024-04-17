package com.example.magic8ball.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.magic8ball.HomeScreen
import com.example.magic8ball.PreviousQuestions
import com.example.magic8ball.models.Screens

// Composable function that sets up the navigation of the application,
// Create the destinations of HomeScreen and PreviousQuestions
// Used this source to create the bottom bar
// https://www.c-sharpcorner.com/article/material-3-bottom-navigation-bar-in-jetpack-compose/
@Composable
fun NavigationGraph(navController: NavHostController) {
    // The start destination of the program in the Home Screen
    NavHost(navController, startDestination = Screens.HomeScreen.route) {
        // Creating the Home Screen
        composable(Screens.HomeScreen.route) {
            HomeScreen()
        }
        // Creating the PreviousQuestions Screen
        composable(Screens.PreviousQuestionsScreen.route) {
            PreviousQuestions()
        }
    }
}