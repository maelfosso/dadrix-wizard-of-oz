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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SignInOTPViewModel(
    savedStateHandle: SavedStateHandle,
    val authRepository: AuthRepository
): ViewModel() {
    companion object {
        private val TAG = SignInOTPViewModel::class.java.name
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                // val myRepository = (this[APPLICATION_KEY] as MyApplication).myRepository
                SignInOTPViewModel(
                    authRepository = WoZApplication.getAppInstance().authRepository,
                    savedStateHandle = savedStateHandle
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
                    Log.d(TAG, "error when checking OTP", it)
                }
                .collect {
                    Log.d(TAG, "checking successful")

                    onSuccess()
                    _uiState.update {
                        it.copy(
                            isChecking = false
                        )
                    }
                }
        }
    }
}

class SignInOTPViewModelFactory(owner: SavedStateRegistryOwner,
                         private val authRepository: AuthRepository,
                         defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = SignInOTPViewModel(handle, authRepository) as T
}
