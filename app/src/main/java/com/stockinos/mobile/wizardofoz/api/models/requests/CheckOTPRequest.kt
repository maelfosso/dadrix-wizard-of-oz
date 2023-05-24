package com.stockinos.mobile.wizardofoz.api.models.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CheckOTPRequest(
    @Json(name = "phone_number") val phoneNumber: String,
    @Json(name = "pin_code") val pinCode: String,
    @Json(name = "language") val language: String
)