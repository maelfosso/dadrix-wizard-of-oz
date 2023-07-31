package com.stockinos.mobile.wizardofoz.ui.conversation

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.gson.Gson
import com.stockinos.mobile.wizardofoz.WoZApplication
import com.stockinos.mobile.wizardofoz.api.models.requests.OnWhatsappMessageReceived
import com.stockinos.mobile.wizardofoz.models.Message
import com.stockinos.mobile.wizardofoz.dao.MessageDao
import com.stockinos.mobile.wizardofoz.services.AuthManager
import io.socket.client.Ack
import io.socket.client.Socket
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class ConversationViewModel(
    savedStateHandle: SavedStateHandle,
    val messageDao: MessageDao,
    val socket: Socket,
    val authManager: AuthManager,
): ViewModel() {
    companion object {
        private val TAG = ConversationViewModel::class.java.name
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                // val myRepository = (this[APPLICATION_KEY] as MyApplication).myRepository
                val messageDao = WoZApplication.getAppInstance().messageDao
                val mSocket = WoZApplication.getAppInstance().mSocket
                ConversationViewModel(
                    savedStateHandle,
                    messageDao = messageDao,
                    socket = mSocket,
                    authManager = AuthManager(WoZApplication.getAppInstance().applicationContext),
                )
            }
        }
    }

    private val user: String = checkNotNull(savedStateHandle["phoneNumber"])
    private val _uiState = MutableStateFlow(ConversationUiState(user = user))
    val uiState: StateFlow<ConversationUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            messageDao.allMessagesAboutUser(user).collect() { messages ->
                _uiState.update { it.copy(messagesItems = messages) }
            }
        }
    }

    fun sendMessage(text: String) {
        Log.d(TAG, "sendMessage : $text")
        val authUser = runBlocking {
            authManager.getUser().first()
        }
        val message = mapOf<String, String>(
            "from" to authUser.phoneNumber,
            "to" to user,
            "message" to text
        )

        viewModelScope.launch(Dispatchers.IO + NonCancellable) {
            socket.emit(
                "whatsapp:message:woz:sent",
                Gson().toJson(message),
                Ack {
                    Log.d("Send Message Text", it[0].toString())
                    val data = Gson().fromJson(it[0].toString(), OnWhatsappMessageReceived::class.java)
                    Log.d("Send Message Text", "on whatsapp:message:received : $data")
                    messageDao.insert(data)
                }
            )
        }
    }
}

