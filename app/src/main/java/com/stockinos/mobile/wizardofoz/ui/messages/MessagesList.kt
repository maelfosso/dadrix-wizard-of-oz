package com.stockinos.mobile.wizardofoz.ui.messages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData

@Composable
fun MessagesList(
    messagesLiveData: LiveData<List<MessagesAboutUser>>,
    onMessageItemClicked: (user: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val messages by messagesLiveData.observeAsState(initial = emptyList())
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        items(
            items = messages,
            key = { message -> message.user.phoneNumber },
        ) { message ->
            MessageItem(message = message, onClick = { onMessageItemClicked(it) })
        }
    }
}