package com.stockinos.mobile.wizardofoz


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.stockinos.mobile.wizardofoz.navigation.NavigationGuest
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesViewModel
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesScreenContent
import com.stockinos.mobile.wizardofoz.ui.theme.WizardOfOzTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainBaseView {
                val navController = rememberNavController()
                NavigationGuest(navController = navController)
            }
        }
    }
}

@Composable
fun MainBaseView(
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
