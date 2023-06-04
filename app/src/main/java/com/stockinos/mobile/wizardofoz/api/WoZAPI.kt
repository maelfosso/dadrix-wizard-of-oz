package com.stockinos.mobile.wizardofoz.api

import com.stockinos.mobile.wizardofoz.api.models.requests.CheckOTPRequest
import com.stockinos.mobile.wizardofoz.api.models.requests.GetOTPRequest
import com.stockinos.mobile.wizardofoz.utils.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface WoZAPI {

    companion object {
        var woZAPI: WoZAPI? = null

        fun getInstance(): WoZAPI {
            if (woZAPI == null) {
                woZAPI = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                    .create(WoZAPI::class.java)
            }

            return woZAPI!!
        }
    }

    @POST("/auth/otp")
    suspend fun getOTP(@Body getOTPRequest: GetOTPRequest): Response<Unit>
    @POST("/auth/otp/check")
    suspend fun checkOTP(@Body checkOTPRequest: CheckOTPRequest): Response<Unit>
    @POST("/auth/otp/resend")
    suspend fun resendOTP(@Body getOTPRequest: GetOTPRequest)
}