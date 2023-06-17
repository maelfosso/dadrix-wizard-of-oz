package com.stockinos.mobile.wizardofoz.ui.signotp

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.savedstate.SavedStateRegistryOwner
import com.stockinos.mobile.wizardofoz.WoZApplication
import com.stockinos.mobile.wizardofoz.api.models.requests.CheckOTPRequest
import com.stockinos.mobile.wizardofoz.repositories.AuthRepository
import com.stockinos.mobile.wizardofoz.services.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SignInOTPViewModel(
    savedStateHandle: SavedStateHandle,
    val tokenManager: TokenManager,
    val authRepository: AuthRepository
): ViewModel() {
    companion object {
        private val TAG = SignInOTPViewModel::class.java.name
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                // val myRepository = (this[APPLICATION_KEY] as MyApplication).myRepository
                SignInOTPViewModel(
                    savedStateHandle = savedStateHandle,
                    tokenManager = TokenManager(WoZApplication.getAppInstance().applicationContext),
                    authRepository = WoZApplication.getAppInstance().authRepository,
                )
            }
        }
    }

    private val phoneNumber: String = checkNotNull(savedStateHandle["phoneNumber"])

    init {
        Log.d(TAG, "PhoneNumber Extracted: $phoneNumber")
        Log.d(TAG, "Keys : ${savedStateHandle.keys()} - ${savedStateHandle.contains("phoneNumber")}")
    }

    private val _uiState = MutableStateFlow(SignInOTPUiState())
    val uiState: StateFlow<SignInOTPUiState> = _uiState.asStateFlow()

    fun handlePinCodeFilled(newPinCode: String) {
        Log.d(TAG, "handlePinCodeFilled: $newPinCode")
        _uiState.update {
            it.copy(
                pinCode = newPinCode
            )
        }
    }

    fun checkOTP(onSuccess: () -> Unit) {
        Log.d(TAG, "checkOTP")
        _uiState.update {
            it.copy(
                isChecking = true
            )
        }

        viewModelScope.launch {
            val body = CheckOTPRequest(
                phoneNumber = phoneNumber,
                pinCode = _uiState.value.pinCode,
                language = "en"
            )
            authRepository.checkOTP(body)
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d(TAG, "error when checking OTP: ${it.message}", it)
                    var error = it.message

                    // check if the error is different than the ones from API
                    if (error?.startsWith("ERR") == false) {
                        error = "UNKNOWN"
                    }
                    _uiState.update { state ->
                        state.copy(
                            error = error!!
                        )
                    }
                }
                .collect { response ->
                    if (response.isSuccessful) {
                        Log.d(TAG, "checking successful: ${response.body()}")
                        val checkOTPResponse = response.body()!!

                        tokenManager.saveToken(checkOTPResponse.token)

                        onSuccess()
                        _uiState.update {
                            it.copy(
                                isChecking = false
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
