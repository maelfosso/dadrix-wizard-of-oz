package com.stockinos.mobile.wizardofoz

import com.google.gson.Gson
import com.stockinos.mobile.wizardofoz.models.User
import com.stockinos.mobile.wizardofoz.models.Message
import com.stockinos.mobile.wizardofoz.ui.conversation.ConversationUiState
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesAboutUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// how to use Figma because I am watching you using it with Material
val messageText = """
{
    id: 'wamid.HBgMMjM3Njc4OTA4OTg5FQIAEhggREZERDlCM0FERTZCRENGNTNFRTY4NzU3MTYxNEI0M0YA',
    from: 'Stock Inos',
    timestamp: '1672344794',
    type: 'text',
    text_id: '00000000-0000-0000-0000-000000000000',
    text: {
        ID: 0,
        CreatedAt: '0001-01-01T00:00:00Z',
        UpdatedAt: '0001-01-01T00:00:00Z',
        DeletedAt: null,
        id: '00000000-0000-0000-0000-000000000000',
        body: "It seems like you know how to use Figma do you think we can help"
    },
    image: {
        ID: 0,
        CreatedAt: '0001-01-01T00:00:00Z',
        UpdatedAt: '0001-01-01T00:00:00Z',
        DeletedAt: null,
        caption: '',
        mime_type: '',
        sha256: '',
        id: ''
    },
    audio: {
        ID: 0,
        CreatedAt: '0001-01-01T00:00:00Z',
        UpdatedAt: '0001-01-01T00:00:00Z',
        DeletedAt: null,
        mime_type: '',
        sha256: '',
        id: '',
        voice: false
    }
}
"""

val message = Gson().fromJson(
    messageText,
    Message::class.java
)
val messageByUser = MessagesAboutUser(
    User("John DOE", phoneNumber = "+237690238423"),
    listOf(message),
    49
)

val messages = listOf(message)