package com.stockinos.mobile.wizardofoz.ui.signin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.stockinos.mobile.wizardofoz.BaseViewModel
import com.stockinos.mobile.wizardofoz.CoroutinesHandler
import com.stockinos.mobile.wizardofoz.WoZApplication
import com.stockinos.mobile.wizardofoz.api.helpers.ApiResponse
import com.stockinos.mobile.wizardofoz.api.models.requests.GetOTPRequest
import com.stockinos.mobile.wizardofoz.repositories.AuthRepository
import kotlinx.coroutines.flow.*

class SignInViewModel(
    val authRepository: AuthRepository
): BaseViewModel() {
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

    fun signIn(onSuccess: (phoneNumber: String) -> Unit) = baseRequest(
        object: CoroutinesHandler<ApiResponse<Unit>> {
            override fun onSuccess(response: ApiResponse<Unit>) {
                when(response) {
                    is ApiResponse.Failure -> {
                        _uiState.update {
                            it.copy(
                                error = response.errorMessage,
                                isSignIn = false
                            )
                        }
                    }
                    ApiResponse.Loading -> {
                        _uiState.update {
                            it.copy(
                                isSignIn = true
                            )
                        }
                    }
                    is ApiResponse.Success -> {
                        onSuccess(_uiState.value.phoneNumber)
                        _uiState.update {
                            it.copy(
                                isSignIn = false
                            )
                        }
                    }
                }
            }
            override fun onError(message: String) {
                Log.d(TAG, "error when signIn: $message")

                _uiState.update {
                    it.copy(
                        error = message
                    )
                }
            }
        }
    ) {
        val getOTPRequest = GetOTPRequest(
            phoneNumber = _uiState.value.phoneNumber,
            language = "en"
        )
        authRepository.getOTP(getOTPRequest)
    }
}


// fun signIn(onSuccess: (phoneNumber: String) -> Unit) {
//     Log.d(TAG, "signIn")
//     _uiState.update {
//         it.copy(
//             isSignIn = true
//         )
//     }
//
//     viewModelScope.launch {
//         val currentPhoneNumber = _uiState.value.phoneNumber
//         val body = GetOTPRequest(
//             phoneNumber = currentPhoneNumber,
//             language = "en"
//         )
//         authRepository.getOTP(body)
//             .flowOn(Dispatchers.IO)
//             .catch {
//                 Log.d(TAG, "error when getting OTP: ${it.message}", it)
//             }
//             .collect { response ->
//                 Log.d(TAG, "auth successful")
//                 if (response.isSuccessful) {
//                     onSuccess(currentPhoneNumber)
//
//                     _uiState.update {
//                         it.copy(
//                             isSignIn = false
//                         )
//                     }
//                 } else {
//                     var error = response.errorBody()!!.string().trim()
//                     Log.d(TAG, "ERROR!!! : $error")
//
//                     // check if the error is different than the ones from API
//                     if (!error.startsWith("ERR")) {
//                         error = "UNKNOWN"
//                     }
//                     Log.d(TAG, "ERROR!!! 2x : $error")
//                     _uiState.update {
//                         it.copy(
//                             error = error
//                         )
//                     }
//                 }
//             }
//     }
// }