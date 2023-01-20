package com.stockinos.mobile.wizardofoz.ui.conversation

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage

data class ConversationUiState(
    val messagesItems: List<WhatsappMessage> = listOf()
)
