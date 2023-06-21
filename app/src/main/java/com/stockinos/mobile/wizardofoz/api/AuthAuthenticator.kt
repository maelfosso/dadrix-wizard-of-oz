package com.stockinos.mobile.wizardofoz.api

import com.stockinos.mobile.wizardofoz.services.AuthManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*

class AuthAuthenticator(
    // private val refreshToken: () retrofit2.Response<RefreshTokenResponse>,
    private val woZAPI: WoZAPI,
    private val authManager: AuthManager
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            authManager.getToken().first()
        }

        return runBlocking {
            val newToken = woZAPI.refreshToken("Bearer $token") // getNewToken(token)
            // val newToken = refreshToken("Bearer $token")

            // Couldn't refresh the token, so restart the login process
            if (!newToken.isSuccessful || newToken.body() == null) {
                authManager.deleteToken()
            }

            newToken.body()?.let {
                authManager.saveToken(it.token)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.token}")
                    .build()
            }
        }
    }
}