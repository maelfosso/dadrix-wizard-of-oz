package com.stockinos.mobile.wizardofoz.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stockinos.mobile.wizardofoz.ui.MessagesList
import com.stockinos.mobile.wizardofoz.ui.MessagesViewModel

@Composable
fun MessagesScreen(
    modifier: Modifier = Modifier,
    messagesViewModel: MessagesViewModel = viewModel()
) {
    Column(modifier = modifier) {
        MessagesList(
            messagesLiveData = messagesViewModel.allMessages
        )
    }
}