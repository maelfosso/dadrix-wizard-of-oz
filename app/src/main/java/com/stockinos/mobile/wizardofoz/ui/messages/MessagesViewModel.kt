package com.stockinos.mobile.wizardofoz.ui.messages

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.stockinos.mobile.wizardofoz.WoZApplication
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import com.stockinos.mobile.wizardofoz.dao.WhatsappMessageDao
import com.stockinos.mobile.wizardofoz.services.AuthManager
import com.stockinos.mobile.wizardofoz.ui.signotp.SignInOTPViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MessagesViewModel(
    private val whatsappMessageDao: WhatsappMessageDao,
    private val authManager: AuthManager,
): ViewModel() {
    companion object {
        private val TAG = SignInOTPViewModel::class.java.name
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                // val myRepository = (this[APPLICATION_KEY] as MyApplication).myRepository
                MessagesViewModel(
                    whatsappMessageDao = WoZApplication.getAppInstance().whatsappMessageDao,
                    authManager = AuthManager(WoZApplication.getAppInstance().applicationContext),
                )
            }
        }
    }

    init {
        WoZApplication.getAppInstance().connectSocket()
    }

    val allMessages: LiveData<List<WhatsappMessage>> = whatsappMessageDao.allWhatsappMessages
        .asLiveData()

    val allMessagesAboutUser: LiveData<List<MessagesByUser>> = allMessages.map { it ->
        val authUser =  runBlocking {
            authManager.getUser().first()
        }

        val userFrom = it.groupBy { it.from }
        val userTo = it.groupBy { it.to }
        (userFrom.asSequence() + userTo.asSequence())
            .groupBy({ it.key }, { it.value })
            .mapValues { (key, values) -> values.flatten() }
            .filterKeys { user -> !(user.isNullOrEmpty() || user.isNullOrBlank()) && user != authUser.phoneNumber }
            .mapValues { (user, messages) -> MessagesByUser(
                user!!,
                messages,
                messages.count { it.state == "unread" }
            )}
            .values.map { it }
    }

    fun insert(message: WhatsappMessage) = viewModelScope.launch {
        whatsappMessageDao.insert(message)
    }

}

