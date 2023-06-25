package com.stockinos.mobile.wizardofoz.viewmodels

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.stockinos.mobile.wizardofoz.WoZApplication
import com.stockinos.mobile.wizardofoz.services.AuthManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TokenViewModel(
    private val authManager: AuthManager,
): ViewModel() {

    companion object {
        private val TAG = TokenViewModel::class.java.name
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                TokenViewModel(
                    authManager = AuthManager(WoZApplication.getAppInstance().applicationContext),
                )
            }
        }
    }
    val token = MutableLiveData<String?>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            authManager.getToken().collect {
                withContext(Dispatchers.Main) {
                    token.value = it
                }
            }
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authManager.saveToken(token)
        }
    }

    fun deleteToken() {
        viewModelScope.launch(Dispatchers.IO) {
            authManager.deleteToken()
        }
    }
}