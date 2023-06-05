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
import com.stockinos.mobile.wizardofoz.ui.MessageItem

@Composable
fun MessagesList(
    messagesLiveData: LiveData<List<MessagesByUser>>,
    onMessageItemClicked: (user: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val messages by messagesLiveData.observeAsState(initial = emptyList())
    Log.i("MessagesList", "Count Messages ${messagesLiveData.value?.size.toString()}")
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        items(
            items = messages,
            key = { message -> message.user },
        ) { message ->
            MessageItem(message = message, onClick = { onMessageItemClicked(it) })
        }
    }
}