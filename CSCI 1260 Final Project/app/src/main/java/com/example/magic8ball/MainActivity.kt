package com.example.magic8ball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.magic8ball.ui.theme.Magic8BallTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Magic8BallTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(64.dp))

                        Magic8Ball()
                        // Add some spacing above the button
                        Spacer(modifier = Modifier.height(32.dp))
                        // Ask Magic 8 Ball Button
                        Button(onClick = { viewModel.askQuestion() }) {
                            Text(text = "Ask me a question")
                        }
                        // Add some spacing above the button
                        Spacer(modifier = Modifier.height(32.dp))
                        // Ask Magic 8 Ball Button
                        Button(onClick = { viewModel.askBiasedQuestion("What is the meaning of life?") }) {
                            Text(text = "I'm Feeling Lucky!")
                        }
                        // Add some spacing below the button
                        Spacer(modifier = Modifier.height(64.dp))
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
    Image(modifier = Modifier.size(250.dp),
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