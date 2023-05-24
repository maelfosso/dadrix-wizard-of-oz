package com.stockinos.mobile.wizardofoz.api.models.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetOTPRequest(
    @Json(name = "phone_number") val phoneNumber: String,
    @Json(name = "language") val language: String
)