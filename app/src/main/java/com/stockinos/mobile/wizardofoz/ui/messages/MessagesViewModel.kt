package com.stockinos.mobile.wizardofoz.ui.messages

import androidx.lifecycle.*
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import com.stockinos.mobile.wizardofoz.dao.WhatsappMessageDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MessagesViewModel(private val repository: WhatsappMessageDao): ViewModel() {

    val allMessages: LiveData<List<WhatsappMessage>> = repository.allWhatsappMessages
        .asLiveData()

    val allMessagesByUser: LiveData<List<MessagesByUser>> = allMessages.map { it ->
        it.groupBy { it.from }
            .filter { (from, messages) -> from != "inner" }
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

class MessagesViewModelFactory(private val repository: WhatsappMessageDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessagesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MessagesViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
