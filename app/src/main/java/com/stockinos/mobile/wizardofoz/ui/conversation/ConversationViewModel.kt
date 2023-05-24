package com.stockinos.mobile.wizardofoz.ui.conversation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import com.stockinos.mobile.wizardofoz.dao.WhatsappMessageDao
import io.socket.client.Ack
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ConversationViewModel(
    private val repository: WhatsappMessageDao,
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
        viewModelScope.launch {
            repository.allWhatsappMessagesAboutUser(user).collect() { messages ->
                _uiState.update { it.copy(messagesItems = messages) }
            }
        }
    }

    fun sendMessage(text: String) {
        Log.d(TAG, "sendMessage : $text")
        val message = mapOf<String, String>(
            "from" to "inner",
            "to" to user,
            "message" to text
        )

        // fetchJob?.cancel()
        // fetchJob =
        viewModelScope.launch(Dispatchers.IO + NonCancellable) {
            socket.emit(
                "whatsapp:message:woz:sent",
                Gson().toJson(message),
                Ack {
                    Log.d("Send Message Text", it[0].toString())
                    val data = Gson().fromJson(it[0].toString(), WhatsappMessage::class.java)
                    Log.d("Send Message Text", "on whatsapp:message:received : $data")
                    repository.insert(data)
                }
            )
        }
    }
}

class ConversationViewModelFactory(
    private val repository: WhatsappMessageDao,
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
