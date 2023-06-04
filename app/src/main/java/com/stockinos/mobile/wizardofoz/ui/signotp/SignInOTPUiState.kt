package com.stockinos.mobile.wizardofoz.ui.signotp

data class SignInOTPUiState(
    val pinCode: String = "",
    val isChecking: Boolean = false,
    val error: String = ""
) {
    fun isError() = error.isNotBlank() || error.isNotEmpty()
}
