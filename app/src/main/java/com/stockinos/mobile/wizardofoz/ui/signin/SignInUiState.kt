package com.stockinos.mobile.wizardofoz.ui.signin

data class SignInUiState(
    val phoneNumber: String = "",
    val isSignIn: Boolean = false,
    val error: String = ""
) {
    fun isError() = error.isNotBlank() || error.isNotEmpty()
}