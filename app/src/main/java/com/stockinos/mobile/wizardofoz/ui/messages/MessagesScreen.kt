package com.stockinos.mobile.wizardofoz.ui.messages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesViewModel

@Composable
fun MessagesScreen(
    modifier: Modifier = Modifier,
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
                messagesLiveData = messagesViewModel.allMessagesByUser
            )
        }
    }
}