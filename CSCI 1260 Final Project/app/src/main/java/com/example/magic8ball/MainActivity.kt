package com.example.magic8ball

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.example.magic8ball.ui.theme.Magic8BallTheme


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainActivityViewModel

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Magic8BallTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                   // Alligns everything in a column within the page
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(64.dp))
                        Magic8Ball()
                        // Add some spacing above the button
                        Spacer(modifier = Modifier.height(32.dp))
                        // Ask Magic 8 Ball Button
                        Button(onClick = { viewModel.askQuestion() }) {
                            Magic8Ball()
                        }
                        // Adding space
                        Spacer(modifier = Modifier.height(32.dp))
                        // Creating a button for the user that calls the response model when pushed
                        Button(onClick = { viewModel.askBiasedQuestion(question = viewModel.responseModel.question)}) {
                            Text(text = "I'm Feeling Lucky!")

                        }
                        // Add some spacing below the button
                        Spacer(modifier = Modifier.height(64.dp))

                        // Used Stack Overflow for help to hide the keyboard
                        // https://stackoverflow.com/questions/59133100/how-to-close-the-virtual-keyboard-from-a-jetpack-compose-textfield/66259111#66259111
                        val keyboardController = LocalSoftwareKeyboardController.current
                        // A text box for the user to enter their question for the magic 8 ball
                        TextField(value = viewModel.responseModel.question, onValueChange = { viewModel.questionAsked(it)},  keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                        )

                        // Adding space in between the text boxes
                        Spacer(modifier = Modifier.height(30.dp))
                        // A textbox that shows the magic 8 balls response
                        TextField(value = viewModel.responseModel.response, onValueChange = { /*TODO*/ }, enabled = false)
                    }
                }
            }
        }
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }
}

@Composable
fun Magic8Ball() {
    Image(modifier = Modifier.size(100.dp),
    painter = painterResource(id = R.drawable.eightball),
    contentDescription = null)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Magic8BallTheme {
        Magic8Ball()
    }
}




