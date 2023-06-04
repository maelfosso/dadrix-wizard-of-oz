package com.stockinos.mobile.wizardofoz.ui.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.stockinos.mobile.wizardofoz.WoZApplication
import com.stockinos.mobile.wizardofoz.api.models.requests.GetOTPRequest
import com.stockinos.mobile.wizardofoz.dao.WhatsappMessageDao
import com.stockinos.mobile.wizardofoz.repositories.AuthRepository
import com.stockinos.mobile.wizardofoz.ui.conversation.ConversationViewModel
import com.stockinos.mobile.wizardofoz.ui.signotp.SignInOTPViewModel
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SignInViewModel(
    val authRepository: AuthRepository
): ViewModel() {
    companion object {
        private val TAG = SignInViewModel::class.java.name
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // val savedStateHandle = createSavedStateHandle()
                SignInViewModel(
                    authRepository = WoZApplication.getAppInstance().authRepository,
                    // savedStateHandle = savedStateHandle
                )
            }
        }
    }

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun handlePhoneNumberChanges(newPhoneNumber: String) {
        Log.d(TAG, "handlePhoneNumberChanges : $newPhoneNumber")
        _uiState.update {
            it.copy(
                phoneNumber = newPhoneNumber
            )
        }
    }

    fun signIn(onSuccess: (phoneNumber: String) -> Unit) {
        Log.d(TAG, "signIn")
        _uiState.update {
            it.copy(
                isSignIn = true
            )
        }

        viewModelScope.launch {
            val currentPhoneNumber = _uiState.value.phoneNumber
            val body = GetOTPRequest(
                phoneNumber = currentPhoneNumber,
                language = "en"
            )
            authRepository.getOTP(body)
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d(TAG, "error when getting OTP: ${it.message}", it)
                }
                .collect { response ->
                    Log.d(TAG, "auth successful")
                    if (response.isSuccessful) {
                        onSuccess(currentPhoneNumber)

                        _uiState.update {
                            it.copy(
                                isSignIn = false
                            )
                        }
                    } else {
                        var error = response.errorBody()!!.string().trim()
                        Log.d(TAG, "ERROR!!! : $error")

                        // check if the error is different than the ones from API
                        if (!error.startsWith("ERR")) {
                            error = "UNKNOWN"
                        }
                        Log.d(TAG, "ERROR!!! 2x : $error")
                        _uiState.update {
                            it.copy(
                                error = error
                            )
                        }
                    }
                }
        }
    }
}
