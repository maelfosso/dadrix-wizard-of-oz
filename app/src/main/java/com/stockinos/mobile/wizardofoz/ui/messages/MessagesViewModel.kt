package com.stockinos.mobile.wizardofoz.ui.messages

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.stockinos.mobile.wizardofoz.WoZApplication
import com.stockinos.mobile.wizardofoz.dao.UserDao
import com.stockinos.mobile.wizardofoz.models.Message
import com.stockinos.mobile.wizardofoz.dao.MessageDao
import com.stockinos.mobile.wizardofoz.services.AuthManager
import com.stockinos.mobile.wizardofoz.ui.signotp.SignInOTPViewModel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MessagesViewModel(
    private val messageDao: MessageDao,
    private val userDao: UserDao,
    private val authManager: AuthManager,
): ViewModel() {
    companion object {
        private val TAG = SignInOTPViewModel::class.java.name
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                // val myRepository = (this[APPLICATION_KEY] as MyApplication).myRepository
                MessagesViewModel(
                    messageDao = WoZApplication.getAppInstance().messageDao,
                    userDao = WoZApplication.getAppInstance().userDao,
                    authManager = AuthManager(WoZApplication.getAppInstance().applicationContext),
                )
            }
        }
    }

    init {
        WoZApplication.getAppInstance().connectSocket()
    }

    val allMessagesAboutUser: LiveData<List<MessagesAboutUser>> = userDao.getMessagesAboutUser()
        .asLiveData()

}

