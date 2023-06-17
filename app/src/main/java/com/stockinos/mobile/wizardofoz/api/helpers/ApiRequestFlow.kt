package com.stockinos.mobile.wizardofoz.api.helpers

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.stockinos.mobile.wizardofoz.api.models.responses.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

fun<T> apiRequestFlow(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
    emit(ApiResponse.Loading)

    withTimeoutOrNull(20000L) {
        val response = call()

        try {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(ApiResponse.Success(data))
                }
            } else {
                response.errorBody()?.charStream()?.let { error ->
                    error.close()
                    val s = error.toString()

                    val moshi =  Moshi.Builder().build()
                    val adapter = moshi
                        .adapter(ErrorResponse::class.java)
                    val parsedError: ErrorResponse = adapter
                        .fromJson(s)!!
                    emit(ApiResponse.Failure(parsedError.message, parsedError.code))
                }
            }
        } catch (e: Exception) {
            emit(ApiResponse.Failure( "UNKNOWN", 400)) // e.message ?: e.toString()
        }
    } ?: emit(ApiResponse.Failure("TIMEOUT", 408)) // "Timeout! Please try again."
}.flowOn(Dispatchers.IO)
