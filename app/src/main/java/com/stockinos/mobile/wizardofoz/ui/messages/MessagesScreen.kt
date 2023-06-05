package com.stockinos.mobile.wizardofoz.ui.messages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.stockinos.mobile.wizardofoz.navigation.Routes
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
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

@Composable
fun MessagesScreenContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    messagesViewModel: MessagesViewModel = viewModel()
) {
    Log.i("MessagesScreen", "MsgScreen : ${messagesViewModel.allMessages.value?.size}")
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            MessagesList(
                messagesLiveData = messagesViewModel.allMessagesByUser,
                onMessageItemClicked = { user ->
                    navController.navigate("conversations/$user")
                }
            )
        }
    }
}