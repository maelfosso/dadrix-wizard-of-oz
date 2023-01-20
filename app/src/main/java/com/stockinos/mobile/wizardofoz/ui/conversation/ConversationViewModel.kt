package com.stockinos.mobile.wizardofoz.ui.conversation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.stockinos.mobile.wizardofoz.repositories.WhatsappMessageRepository
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

class ConversationViewModel(
    private val repository: WhatsappMessageRepository,
    private val user: String
): ViewModel() {
    companion object {
        private val TAG = ConversationViewModel::class.java.name
    }

    private val _uiState = MutableStateFlow(ConversationUiState())
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

class ConversationViewModelFactory(private val repository: WhatsappMessageRepository, private val user: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConversationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConversationViewModel(repository, user) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
