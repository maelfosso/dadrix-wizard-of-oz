package com.stockinos.mobile.wizardofoz

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

open class BaseViewModel() : ViewModel() {
    companion object {
        val TAG = BaseViewModel::class.java.name
    }
    private var mJob: Job? = null

    protected fun <T> baseRequest(handler: CoroutinesHandler<T>, request: () -> Flow<T>) {
        mJob = viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            viewModelScope.launch(Dispatchers.Main) {
                Log.d(TAG, "error when getting OTP: $message")

                var error = error.localizedMessage.trim()
                if (!error.startsWith("ERR")) {
                    error = "UNKNOWN"
                }
                handler.onError(error)
            }
        }) {
            request().collect {
                withContext(Dispatchers.Main) {
                    handler.onSuccess(it)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mJob?.let {
            if (it.isActive) {
                it.cancel()
            }
        }
    }
}

interface CoroutinesHandler<T> {
    fun onError(message: String)
    fun onSuccess(data: T)
}
