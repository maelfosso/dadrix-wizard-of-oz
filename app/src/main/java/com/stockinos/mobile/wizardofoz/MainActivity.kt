package com.stockinos.mobile.wizardofoz


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.stockinos.mobile.wizardofoz.navigation.Navigation
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesViewModel
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesViewModelFactory
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesScreen
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesScreenContent
import com.stockinos.mobile.wizardofoz.ui.theme.WizardOfOzTheme

class MainActivity : ComponentActivity() {

    private val messagesViewModel: MessagesViewModel by viewModels {
        MessagesViewModelFactory((application as WoZApplication).whatsappMessageDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BaseView {
                val navController = rememberNavController()
                Navigation(navController = navController)
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
fun BaseView(
    content: @Composable () -> Unit
) {
    WizardOfOzTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background // colors.background
        ) {
            // MessagesWidget(messagesViewModel = messagesViewModel)
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesWidget(
    navController: NavController,
    messagesViewModel: MessagesViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
                 CenterAlignedTopAppBar(
                     modifier = Modifier,
                     title = {
                         Text(
                             text = "Messages",
                             color = Color.Black
                         )
                     },
                     navigationIcon = {
                         IconButton(onClick = { /*TODO*/ }) {
                             Icon(Icons.Filled.Menu, "ContentDescription")
                         }
                     },
                 )
        },
        content = { innerPadding ->
            MessagesScreenContent(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize(),
                navController = navController,
                messagesViewModel = messagesViewModel
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WizardOfOzTheme {
        MessagesWidget(navController = rememberNavController())
    }
}
