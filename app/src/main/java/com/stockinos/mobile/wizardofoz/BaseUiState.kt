package com.stockinos.mobile.wizardofoz

open class BaseUiState(
    open val error: String = ""
) {
    fun isError() = error.isNotBlank() || error.isNotEmpty()
}