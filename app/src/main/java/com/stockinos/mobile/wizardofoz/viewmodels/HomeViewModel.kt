package com.stockinos.mobile.wizardofoz.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel: ViewModel() {
    companion object {
        private val TAG = HomeViewModel::class.java.name
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // val savedStateHandle = createSavedStateHandle()
                HomeViewModel()
            }
        }
    }

    private val _drawerShouldBeOpened = MutableStateFlow(true)
    val drawerShouldBeOpened = _drawerShouldBeOpened.asStateFlow()

    fun openDrawer() {
        _drawerShouldBeOpened.value = true
        Log.d(TAG, "openDrawer()")
    }

    fun resetOpenDrawerAction() {
        _drawerShouldBeOpened.value = false
        Log.d(TAG, "resetOpenDrawerAction()")
    }
}