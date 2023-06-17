package com.stockinos.mobile.wizardofoz.ui.signin

import com.stockinos.mobile.wizardofoz.BaseUiState

data class SignInUiState(
    override val error: String = "",
    val phoneNumber: String = "",
    val isSignIn: Boolean = false,
) : BaseUiState(error)