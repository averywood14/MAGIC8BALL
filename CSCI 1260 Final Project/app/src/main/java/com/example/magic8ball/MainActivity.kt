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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.magic8ball.ui.theme.Magic8BallTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Magic8BallTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(64.dp))

                        Magic8Ball()

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Ask me a Question")
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Magic8Ball() {
    Image(modifier = Modifier.size(250.dp)),
    painter = painterResource(id = R.drawable.eightball),
    contentDescription = null
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Magic8BallTheme {
        Magic8Ball()
    }
}