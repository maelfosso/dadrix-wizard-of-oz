package com.stockinos.mobile.wizardofoz.ui.messages

import android.util.Log
import androidx.lifecycle.*
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import com.stockinos.mobile.wizardofoz.repositories.WhatsappMessageRepository
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesByUser
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MessagesViewModel(private val repository: WhatsappMessageRepository): ViewModel() {

    val allMessages: LiveData<List<WhatsappMessage>> = repository.allWhatsappMessages
        .asLiveData()

    val allMessagesByUser: LiveData<List<MessagesByUser>> = allMessages.map { it ->
        it.groupBy { it.from }
            .mapValues { (from, messages) ->
                MessagesByUser(
                    from,
                    messages,
                    messages.count { it.state == "unread" })
            }
            .values.map { it }
    }

    fun insert(message: WhatsappMessage) = viewModelScope.launch {
        repository.insert(message)
    }

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
