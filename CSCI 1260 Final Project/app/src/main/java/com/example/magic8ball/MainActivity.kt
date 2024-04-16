package com.example.magic8ball

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.magic8ball.ui.theme.Magic8BallTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Magic8BallTheme {

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

@Composable
fun Magic8Ball() {
    Image(modifier = Modifier.size(100.dp),
    painter = painterResource(id = R.drawable.eightball),
    contentDescription = null)
}

sealed class Screens(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    object HomeScreen : Screens(
        route = "home_screen",
        title = "Home",
        icon = Icons.Outlined.Home
    )

    object PreviousQuestions : Screens(
        route = "previousQuestions_screen",
        title = "Previous Questions",
        icon = Icons.Outlined.Star
    )
}

@Composable
fun HomeScreen() {
    val viewModel = MainActivityViewModel()

    var toggleSwitch by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
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
                onValueChange =  { viewModel.questionAsked(it)},
                label = { Text(text = "Ask me a Question")},
                singleLine = true,
                // Hiding the keybord on done
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()})
            )
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Switch(checked = toggleSwitch, onCheckedChange = {toggleSwitch = it})
                Text(text = "8 Ball GPT", textAlign = TextAlign.Center)

            }
        }
    }
}

@Composable
fun PreviousQuestions() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Magenta)
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

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screens.HomeScreen.route) {
        composable(Screens.HomeScreen.route) {
            HomeScreen()
        }
        composable(Screens.PreviousQuestions.route) {
            PreviousQuestions()
        }
    }
}
@Composable
fun BottomBar(
    navController: NavHostController, state: MutableState<Boolean>, modifier: Modifier = Modifier
) {
    val screens = listOf(
        Screens.HomeScreen, Screens.PreviousQuestions
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





//Magic8BallTheme {
//    // A surface container using the 'background' color from the theme
//    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//        // Alligns everything in a column within the page
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Spacer(modifier = Modifier.height(64.dp))
//            Magic8Ball()
//            // Add some spacing above the button
//            Spacer(modifier = Modifier.height(32.dp))
//            // Ask Magic 8 Ball Button
//            Button(onClick = { viewModel.askQuestion() }) {
//                Magic8Ball()
//            }
//            // Adding space
//            Spacer(modifier = Modifier.height(32.dp))
//            // Creating a button for the user that calls the response model when pushed
//            Button(onClick = { viewModel.askBiasedQuestion(question = viewModel.responseModel.question)}) {
//                Text(text = "I'm Feeling Lucky!")
//
//            }
//            // Add some spacing below the button
//            Spacer(modifier = Modifier.height(64.dp))
//
//            // Used Stack Overflow for help to hide the keyboard
//            // https://stackoverflow.com/questions/59133100/how-to-close-the-virtual-keyboard-from-a-jetpack-compose-textfield/66259111#66259111
//            val keyboardController = LocalSoftwareKeyboardController.current
//            // A text box for the user to enter their question for the magic 8 ball
//            TextField(value = viewModel.responseModel.question, onValueChange = { viewModel.questionAsked(it)},  keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
//            )
//
//            // Adding space in between the text boxes
//            Spacer(modifier = Modifier.height(30.dp))
//            // A Text box that shows the magic 8 balls response
//            TextField(value = viewModel.responseModel.response, onValueChange = { /*TODO*/ }, enabled = false)
//        }
//    }
//}
