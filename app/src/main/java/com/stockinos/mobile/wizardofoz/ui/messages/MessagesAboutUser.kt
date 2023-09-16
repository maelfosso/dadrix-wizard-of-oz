package com.stockinos.mobile.wizardofoz.ui.messages

import com.stockinos.mobile.wizardofoz.dto.MessageDTO
import com.stockinos.mobile.wizardofoz.models.User
import com.stockinos.mobile.wizardofoz.models.Message

data class MessagesAboutUser(
    val user: User,
    val messages: List<MessageDTO>,
    val nbUnreadMessages: Int
) {
    val lastMessage = messages.last()
}
