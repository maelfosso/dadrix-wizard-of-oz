package com.stockinos.mobile.wizardofoz.ui.messages

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.stockinos.mobile.wizardofoz.WoZApplication
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import com.stockinos.mobile.wizardofoz.dao.WhatsappMessageDao
import com.stockinos.mobile.wizardofoz.services.AuthManager
import com.stockinos.mobile.wizardofoz.ui.signotp.SignInOTPViewModel
import kotlinx.coroutines.flow.*
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

    val allMessagesByUser: LiveData<List<MessagesByUser>> = allMessages.map { it ->
        val authUser =  runBlocking {
            authManager.getUser().first()
        }

        it.groupBy { it.from }
            .filter { (from, messages) -> from != authUser.phoneNumber }
            .mapValues { (from, messages) ->
                MessagesByUser(
                    authUser.phoneNumber,
                    messages,
                    messages.count { it.state == "unread" })
            }
            .values.map { it }
    }

    fun insert(message: WhatsappMessage) = viewModelScope.launch {
        whatsappMessageDao.insert(message)
    }

}

