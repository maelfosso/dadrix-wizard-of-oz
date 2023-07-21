package com.stockinos.mobile.wizardofoz.api

import com.stockinos.mobile.wizardofoz.WoZApplication
import com.stockinos.mobile.wizardofoz.api.models.requests.CheckOTPRequest
import com.stockinos.mobile.wizardofoz.api.models.requests.GetOTPRequest
import com.stockinos.mobile.wizardofoz.api.models.responses.CheckOTPResponse
import com.stockinos.mobile.wizardofoz.api.models.responses.RefreshTokenResponse
import com.stockinos.mobile.wizardofoz.services.AuthManager
import com.stockinos.mobile.wizardofoz.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface WoZAPI {

    companion object {
        var woZAPI: WoZAPI? = null

        fun getInstance(): WoZAPI {
            val authManager = AuthManager(WoZApplication.getAppInstance().applicationContext)
            val loggingInterceptor = HttpLoggingInterceptor()
            val authInterceptor  = AuthInterceptor(authManager = authManager)
            // val authAuthenticator = AuthAuthenticator(
            //     refreshToken = woZAPI.refreshToken,
            //     tokenManager = tokenManager
            // )

            if (woZAPI == null) {
                woZAPI = Retrofit.Builder()
                    .baseUrl(Constants.API_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(
                        OkHttpClient.Builder()
                            .addInterceptor(loggingInterceptor)
                            .addInterceptor(authInterceptor)
                            // .authenticator(authAuthenticator)
                            .build()
                    )
                    .build()
                    .create(WoZAPI::class.java)

            }

            return woZAPI!!
        }
    }

    @POST("/auth/otp")
    suspend fun getOTP(@Body getOTPRequest: GetOTPRequest): Response<Unit>
    @POST("/auth/otp/check")
    suspend fun checkOTP(@Body checkOTPRequest: CheckOTPRequest): Response<CheckOTPResponse>
    @POST("/auth/otp/resend")
    suspend fun resendOTP(@Body getOTPRequest: GetOTPRequest)

    @POST("/token/refresh")
    suspend fun refreshToken(@Header("Authorization") token: String): Response<RefreshTokenResponse>
    @POST("/token/valid")
    suspend fun isTokenValid(): Response<Boolean>
}