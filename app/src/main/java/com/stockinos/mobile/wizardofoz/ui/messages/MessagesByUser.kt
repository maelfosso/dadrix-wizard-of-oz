package com.stockinos.mobile.wizardofoz.ui.messages

import com.stockinos.mobile.wizardofoz.models.WhatsappMessage

data class MessagesByUser(
    val user: String,
    val messages: List<WhatsappMessage>,
    val nbUnreadMessages: Int
) {
    val lastMessage = messages[messages.size - 1];
}
