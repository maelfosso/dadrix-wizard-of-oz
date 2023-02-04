package com.stockinos.mobile.wizardofoz.ui.conversation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import com.stockinos.mobile.wizardofoz.models.MessageWoZSentData
import com.stockinos.mobile.wizardofoz.models.WhatsappMessageText
import com.stockinos.mobile.wizardofoz.repositories.WhatsappMessageRepository
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Date
import java.util.UUID

class ConversationViewModel(
    private val repository: WhatsappMessageRepository,
    private val socket: Socket,
    private val user: String
): ViewModel() {
    companion object {
        private val TAG = ConversationViewModel::class.java.name
    }

    private val _uiState = MutableStateFlow(ConversationUiState(user = user))
    val uiState: StateFlow<ConversationUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            repository.allWhatsappMessagesAboutUser(user).collect() { messages ->
                _uiState.update { it -> it.copy(messagesItems = messages) }
            }
        }
    }

    fun sendMessage(text: String) {
        Log.d(TAG, "sendMessage : $text")
        val id = (Date().time / 1000).toString()
        val textId = UUID.randomUUID().toString()
        val message = WhatsappMessage(
            id = id,
            from = "inner",
            to = user,
            timestamp = id,

            type = "text",
            textId = textId,
            text = WhatsappMessageText(
                id = textId,
                body = text
            )
        )

        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(Dispatchers.IO + NonCancellable) {
            repository.insert(message)
            socket.emit(
                "whatsapp:message:woz:sent",
                Gson().toJson(message)
            )
        }
    }

    fun fetchMessages() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val items = repository.allWhatsappMessages // all messages related to an user
                _uiState.update {
                    it.copy(messagesItems = items.first())
                }
            } catch (ioe: IOException) {
                Log.d(TAG, ioe.message.toString())
            }
        }
    }
}

class ConversationViewModelFactory(
    private val repository: WhatsappMessageRepository,
    private val socket: Socket,
    private val user: String
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConversationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConversationViewModel(repository, socket, user) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
