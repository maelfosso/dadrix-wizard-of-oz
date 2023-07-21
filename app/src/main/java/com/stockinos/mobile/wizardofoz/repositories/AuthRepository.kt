package com.stockinos.mobile.wizardofoz.repositories

import com.stockinos.mobile.wizardofoz.api.WoZAPI
import com.stockinos.mobile.wizardofoz.api.helpers.apiRequestFlow
import com.stockinos.mobile.wizardofoz.api.models.requests.CheckOTPRequest
import com.stockinos.mobile.wizardofoz.api.models.requests.GetOTPRequest
import kotlinx.coroutines.flow.flow

class AuthRepository(
    val woZAPI: WoZAPI
) {
    fun getOTP(getOTPRequest: GetOTPRequest) = apiRequestFlow {
        woZAPI.getOTP(getOTPRequest)
    }

    fun checkOTP(checkOTPRequest: CheckOTPRequest) =
        flow {
            emit(woZAPI.checkOTP(checkOTPRequest))
        }

    fun isTokenValid() =
        flow {
            emit(woZAPI.isTokenValid())
        }
}