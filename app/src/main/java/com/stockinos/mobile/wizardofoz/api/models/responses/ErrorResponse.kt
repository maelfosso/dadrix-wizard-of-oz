package com.stockinos.mobile.wizardofoz.api.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "code") val code: Int,
    @Json(name = "message") val message: String,
)
