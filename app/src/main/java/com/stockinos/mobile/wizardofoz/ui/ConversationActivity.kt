package com.stockinos.mobile.wizardofoz.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.stockinos.mobile.wizardofoz.ui.theme.WizardOfOzTheme

class ConversationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentIntent = this.intent
        val currentUser = currentIntent.getStringExtra("CUSTOMER")

        // val conversationViewModel: ConversationViewModel by viewModels {
        //     ConversationViewModelFactory(
        //         (application as WoZApplication).whatsappMessageDao,
        //         (application as WoZApplication).mSocket,
        //         currentUser!!
        //     )
        // }

        setContent {
            WizardOfOzTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // ConversationScreen(
                    //     conversationViewModel = conversationViewModel
                    // )
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    WizardOfOzTheme {
        Greeting2("Android")
    }
}