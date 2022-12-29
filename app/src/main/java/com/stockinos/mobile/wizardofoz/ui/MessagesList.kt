package com.stockinos.mobile.wizardofoz.ui

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import com.stockinos.mobile.wizardofoz.ui.theme.MessagesScreen

@Composable
fun MessagesList(
    messagesLiveData: LiveData<List<WhatsappMessage>>,
    modifier: Modifier = Modifier
) {
    val messages by messagesLiveData.observeAsState(initial = emptyList())
    Log.i("MessagesList", messages.size.toString())
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = messages,
            key = { message -> message.id },
        ) { message ->
            MessageItem(message = message)
        }
    }
}