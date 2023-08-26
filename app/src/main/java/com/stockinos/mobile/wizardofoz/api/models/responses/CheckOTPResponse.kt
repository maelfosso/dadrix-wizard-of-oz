package com.stockinos.mobile.wizardofoz.api.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckOTPResponse(
    @Json(name = "name") val name: String,
    @Json(name = "phone_number") val phoneNumber: String,
    @Json(name = "type") val type: String,
    @Json(name = "token") val token: String
)

