package com.stockinos.mobile.wizardofoz.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stockinos.mobile.wizardofoz.ui.MessagesList
import com.stockinos.mobile.wizardofoz.ui.MessagesViewModel
import kotlinx.coroutines.flow.asFlow

@Composable
fun MessagesScreen(
    modifier: Modifier = Modifier,
    messagesViewModel: MessagesViewModel = viewModel()
) {
    Log.i("MessagesScreen", "MsgScreen : ${messagesViewModel.allMessages.value?.size}")
    Column(modifier = modifier) {
        MessagesList(
            messagesLiveData = messagesViewModel.allMessagesByUser
        )
    }
}