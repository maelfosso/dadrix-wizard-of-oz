package com.stockinos.mobile.wizardofoz.ui.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.stockinos.mobile.wizardofoz.api.models.requests.GetOTPRequest
import com.stockinos.mobile.wizardofoz.dao.WhatsappMessageDao
import com.stockinos.mobile.wizardofoz.repositories.AuthRepository
import com.stockinos.mobile.wizardofoz.ui.conversation.ConversationViewModel
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SignInViewModel(
    val authRepository: AuthRepository
): ViewModel() {
    companion object {
        private val TAG = SignInViewModel::class.java.name
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
                    Log.d(TAG, "error when getting OTP", it)
                }
                .collect {
                    Log.d(TAG, "auth successful")

                    onSuccess(currentPhoneNumber)
                    _uiState.update {
                        it.copy(
                            isSignIn = false
                        )
                    }
                }
        }
    }
}

// class SignInViewModelFactory(
//     // private val repository: WhatsappMessageDao,
//     // private val socket: Socket,
//     // private val user: String
// ): ViewModelProvider.Factory {
//     override fun <T : ViewModel> create(modelClass: Class<T>): T {
//         if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
//             @Suppress("UNCHECKED_CAST")
//             return SignInViewModel() as T // (repository, socket, user) as T
//         }
//
//         throw IllegalArgumentException("Unknown ViewModel Class")
//     }
// }
