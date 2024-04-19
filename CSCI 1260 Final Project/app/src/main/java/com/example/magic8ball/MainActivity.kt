package com.example.magic8ball

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
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
import com.example.magic8ball.compose.Answer
import com.example.magic8ball.compose.AnswerAnimation
import com.example.magic8ball.compose.BottomBar
import com.example.magic8ball.compose.Magic8Ball
import com.example.magic8ball.compose.Magic8BallAnimation
import com.example.magic8ball.compose.NavigationGraph
import com.example.magic8ball.compose.ToggleSwitch
import com.example.magic8ball.compose.UserQuestion
import com.example.magic8ball.models.Screens
import com.example.magic8ball.ui.theme.Magic8BallTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
// Composable Function for the Home Screen Layout and functionality
// Used this source to create the bottom bar
// https://www.c-sharpcorner.com/article/material-3-bottom-navigation-bar-in-jetpack-compose/
@Composable
fun HomeScreen() {
    val viewModel : MainActivityViewModel = viewModel()
    val focusManager = LocalFocusManager.current
    val isAsking by viewModel.isAsking.collectAsState(initial = false)

    // Formatting the page
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .wrapContentSize(Alignment.Center)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }

    ) {
        Row(
            // Defining what the row is going to look like
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment =  Alignment.CenterVertically
        ) {
            // Calling the UserQuestion Compose function and setting the view model to the updated view model
            UserQuestion(viewModel = viewModel)
            // Calling the ToggleSwitch Compose Function and setting the view model to the updated view model
            ToggleSwitch(viewModel = viewModel)
        }
        // Creating a Box that contains the magic 8 ball image;
        // This box creates a 3D platform for the Response from the Magic 8 ball,
        // To sit on top of the image
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Calls the Magic 8 Ball Animation Composable - To allows for the image to appear and rotate
            Magic8BallAnimation(
                modifier = Modifier
                    // Makes the image clickable
                    .clickable {
                        focusManager.clearFocus()
                        if(!isAsking && viewModel.responseModel.question.isNotEmpty()) {
                            // If the toggle switch is on the question is a biased question;
                            // If the toggle switch is off the question is a regular question
                            if (viewModel.feelingLucky) {
                                viewModel.askBiasedQuestion(question = viewModel.responseModel.question)
                            } else {
                                viewModel.askQuestion()
                            }
                        }
                }, isAsking = isAsking
            )
            // Calls the Answer Composable - to show the Answer from the Magic 8 Ball
            AnswerAnimation(
                isAsking = isAsking,
                text = viewModel.responseModel.answer,
                // Formatting the text for the response from the Magic 8 ball
                modifier = Modifier
                    .offset(x = (-8).dp, y = (-8).dp)
                    .width(66.dp)
                    .sizeIn(maxHeight = 110.dp)
            )
        }    
    }
}
// Composable function for the Previous Screen layout and functionality
// Used this source to create the bottom bar
// https://www.c-sharpcorner.com/article/material-3-bottom-navigation-bar-in-jetpack-compose/
@Composable
fun PreviousQuestions() {
    // Formatting the Page
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .wrapContentSize(Alignment.Center)
    ) {
        // Formatting the Text on the scren
        Text(
            text = "Previous Questions",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )
    }
}


