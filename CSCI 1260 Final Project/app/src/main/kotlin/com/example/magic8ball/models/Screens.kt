package com.example.magic8ball.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

// Used this source
// https://www.c-sharpcorner.com/article/material-3-bottom-navigation-bar-in-jetpack-compose/
// To create the bottom bar
// Creating a Screens enum with the values each screen should contain;
// And creating those screens
enum class Screens (
    val route : String,
    val title : String,
    val icon : ImageVector
) {
    // Creating the titles of the Home page and giving it an icon
    HomeScreen(
        route = "home_screen",
        title = "Home",
        icon = Icons.Outlined.Home
    ),
    // Creating the title of the Previous Questions page and giving it an icon
    PreviousQuestionsScreen(
        route = "previousQuestions_screen",
        title = "Previous Questions",
        icon = Icons.Outlined.Star
    )

}