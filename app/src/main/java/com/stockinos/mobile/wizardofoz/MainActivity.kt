package com.stockinos.mobile.wizardofoz

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stockinos.mobile.wizardofoz.ui.MessagesViewModel
import com.stockinos.mobile.wizardofoz.ui.MessagesViewModelFactory
import com.stockinos.mobile.wizardofoz.ui.theme.MessagesScreen
import com.stockinos.mobile.wizardofoz.ui.theme.WizardOfOzTheme

class MainActivity : ComponentActivity() {

    private val messagesViewModel: MessagesViewModel by viewModels {
        MessagesViewModelFactory((application as WoZApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WizardOfOzTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                     MessagesWidget(messagesViewModel = messagesViewModel)
                }
            }
        }

        (application as WoZApplication).connectSocket()
    }

    override fun onDestroy() {
        val app = this.application as WoZApplication
        app.disconnectSocket()
        super.onDestroy()
    }
}

@Composable
fun MessagesWidget(messagesViewModel: MessagesViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar (
                title = {
                    Text(
                        text = "Messages",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Menu, "ContentDescription")
                    }
                },
                backgroundColor = Color.Black,
                contentColor = Color.White,
                elevation = 12.dp
            )
        },
        content = {
            MessagesScreen(messagesViewModel = messagesViewModel)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WizardOfOzTheme {
        MessagesWidget()
    }
}