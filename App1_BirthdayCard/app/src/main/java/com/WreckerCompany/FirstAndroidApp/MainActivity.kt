package com.WreckerCompany.FirstAndroidApp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.WreckerCompany.FirstAndroidApp.ui.theme.FirsAndroidAppTheme
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirsAndroidAppTheme {
                // A surface container using the 'background' color from the theme
                val context = this
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    BirthdayGreetingWithImage(stringResource(R.string.happy_birthday_text), stringResource(R.string.signature_text), context)
                }
            }
        }
    }
}

@Composable
fun BirthdayGreetingWithText(message: String, from: String, context: Context?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
            .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(R.drawable.white_cake), contentDescription = null,
                modifier = Modifier .padding(end=10.dp))
            Text(text = message, fontSize = 36.sp)
        }
        Text(text = from, fontSize = 24.sp, modifier = Modifier
            .padding(top = 16.dp, end = 16.dp)
            .align(alignment = Alignment.End))

        Spacer(modifier = Modifier.height(550.dp))

        Button(onClick = {
            if (context != null){
                sendToast(context)
            }
        }) {
            Text(text = stringResource(R.string.open_present_text), fontSize = 24.sp, modifier = Modifier
                .padding(start = 25.dp, top = 16.dp, end = 25.dp, bottom = 16.dp))
        }
    }
}

fun sendToast(context: Context){
    Toast.makeText(context, "HAPPY BIRTHDAY!!", Toast.LENGTH_LONG).show()
}

@Composable
fun BirthdayGreetingWithImage(message: String, from: String, context: Context? = null, modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.androidparty)
    Box{
        Image(painter = image, contentDescription = null, contentScale = ContentScale.Crop)
        BirthdayGreetingWithText(message, from, context, modifier)
    }
}

@Preview(name = "MyPreview", showBackground = false, showSystemUi = false)
@Composable
fun DefaultPreview() {
    FirsAndroidAppTheme {
        BirthdayGreetingWithImage(stringResource(R.string.happy_birthday_text), stringResource(R.string.signature_text))
    }
}