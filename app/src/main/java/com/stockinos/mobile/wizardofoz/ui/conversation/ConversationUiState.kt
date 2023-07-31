package com.stockinos.mobile.wizardofoz.ui.conversation

import com.stockinos.mobile.wizardofoz.models.Message
import com.stockinos.mobile.wizardofoz.models.MessageWithUser

data class ConversationUiState(
    val messagesItems: List<MessageWithUser> = listOf(),
    val user: String
)
