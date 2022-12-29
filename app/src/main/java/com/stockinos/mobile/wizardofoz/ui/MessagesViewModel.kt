package com.stockinos.mobile.wizardofoz.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.*
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import com.stockinos.mobile.wizardofoz.repositories.WhatsappMessageRepository
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MessagesViewModel(private val repository: WhatsappMessageRepository): ViewModel() {

    //
    val allMessages: LiveData<List<WhatsappMessage>> = repository.allWhatsappMessages.asLiveData()

    fun insert(message: WhatsappMessage) = viewModelScope.launch {
        repository.insert(message)
    }
//    private val _messages = mutableStateListOf<WhatsappMessage>(emptyList<WhatsappMessage>())
//    val messages: List<WhatsappMessage>
//        get() = _messages
//
//    fun add(item: WhatsappMessage) {
//        _messages.add(item)
//    }
}

class MessagesViewModelFactory(private val repository: WhatsappMessageRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessagesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MessagesViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
