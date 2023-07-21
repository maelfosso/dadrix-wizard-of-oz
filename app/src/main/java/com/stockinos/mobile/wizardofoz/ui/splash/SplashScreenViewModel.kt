package com.stockinos.mobile.wizardofoz.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.stockinos.mobile.wizardofoz.WoZApplication
import com.stockinos.mobile.wizardofoz.repositories.AuthRepository
import com.stockinos.mobile.wizardofoz.services.AuthManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    val authRepository: AuthRepository,
): ViewModel() {
    companion object {
        private val TAG = SplashScreenViewModel::class.java.name
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SplashScreenViewModel(
                    authRepository = WoZApplication.getAppInstance().authRepository
                )
            }
        }
    }

    private val _uiState = MutableStateFlow(SplashScreenUiState())
    val uiState: StateFlow<SplashScreenUiState> = _uiState.asStateFlow()

    init {
        checkJwtToken()
    }

    private fun checkJwtToken() {
        _uiState.update {
            it.copy(
                isChecking = true
            )
        }
        viewModelScope.launch {
            authRepository.isTokenValid()
                .flowOn(Dispatchers.IO)
                .catch {
                    _uiState.update {
                        it.copy(
                            isValid = false,
                            isChecking = false,
                        )
                    }
                }
                .collect { response ->
                    if (response.isSuccessful) {
                        val isTokenValidResponse = response.body()!!

                        _uiState.update {
                            it.copy(
                                isValid = isTokenValidResponse,
                                isChecking = false
                            )
                        }
                    } else {
                        // There is certainly some errors.
                        // But it is not important here
                        _uiState.update {
                            it.copy(
                                isValid = false,
                                isChecking = false
                            )
                        }
                    }
                }
        }
    }

}