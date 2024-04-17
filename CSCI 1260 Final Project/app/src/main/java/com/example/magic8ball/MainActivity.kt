package com.example.magic8ball

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.magic8ball.models.Screens
import com.example.magic8ball.ui.theme.Magic8BallTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Magic8BallTheme {
                // Used this source to create the bottom bar
                // https://www.c-sharpcorner.com/article/material-3-bottom-navigation-bar-in-jetpack-compose/
                val navController: NavHostController = rememberNavController()
                val buttonsVisible = remember { mutableStateOf(true) }

                Scaffold(
                    bottomBar = {
                        BottomBar(
                            navController = navController,
                            state = buttonsVisible,
                            modifier = Modifier
                        )
                    }) { paddingValues ->
                    Box(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        NavigationGraph(navController = navController)
                    }
                }
            }
        }
    }
}

// Composable Function for the Home Screen Layout
// Used this source to create the bottom bar
// https://www.c-sharpcorner.com/article/material-3-bottom-navigation-bar-in-jetpack-compose/
@Composable
fun HomeScreen() {
    val viewModel : MainActivityViewModel = viewModel()

    var toggleSwitch by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    // Formatting the page
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .wrapContentSize(Alignment.Center)
    ) {
        Row(
            // Defining what the row is going to look like
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment =  Alignment.CenterVertically
        ) {
          // Laying out whats inside the row
            TextField(value = viewModel.responseModel.question,
                onValueChange = { viewModel.questionAsked(it)},
                label = { Text(text = "Ask me a Question")},
                singleLine = true,
                // Hiding the keybord on done
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()})
            )
            // Formatting the Toggle Switch next to the question input
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Switch(checked = toggleSwitch, onCheckedChange = {toggleSwitch = it})
                Text(text = "Feeling Lucky?", textAlign = TextAlign.Center, color = Color.Black,
                    modifier = Modifier
                        .size(64.dp)
                )
            }
        }
        Text(text = viewModel.responseModel.answer, color = Color.White)
        // Formatting the Magic 8 ball
        Image(
            painter = painterResource(id = R.drawable.magicball),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clickable {
                    // If the toggle switch is on the question is a biased question;
                    // If the toggle switch is off the question is a regular question
                    if (toggleSwitch) {
                        viewModel.askBiasedQuestion(question = viewModel.responseModel.question)
                    } else {
                        viewModel.askQuestion()
                    }
                }
        )
    }
}

// Used this source to create the bottom bar
// https://www.c-sharpcorner.com/article/material-3-bottom-navigation-bar-in-jetpack-compose/
@Composable
fun PreviousQuestions() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Previous Questions",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )
    }
}

// Used this source to create the bottom bar
// https://www.c-sharpcorner.com/article/material-3-bottom-navigation-bar-in-jetpack-compose/
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screens.HomeScreen.route) {
        composable(Screens.HomeScreen.route) {
            HomeScreen()
        }
        composable(Screens.PreviousQuestionsScreen.route) {
            PreviousQuestions()
        }
    }
}
// Used this source to create the bottom bar
// https://www.c-sharpcorner.com/article/material-3-bottom-navigation-bar-in-jetpack-compose/
@Composable
fun BottomBar(
    navController: NavHostController, state: MutableState<Boolean>, modifier: Modifier = Modifier
) {
    val screens = listOf(
        Screens.HomeScreen, Screens.PreviousQuestionsScreen
    )

    NavigationBar(
        modifier = modifier,
        containerColor = Color.LightGray,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->

            NavigationBarItem(
                label = {
                    Text(text = screen.title!!)
                },
                icon = {
                    Icon(imageVector = screen.icon!!, contentDescription = "")
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.Gray, selectedTextColor = Color.White
                ),
            )
        }
    }
}

