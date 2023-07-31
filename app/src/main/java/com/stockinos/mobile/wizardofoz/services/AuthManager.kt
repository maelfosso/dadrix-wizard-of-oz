package com.stockinos.mobile.wizardofoz.services

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.stockinos.mobile.wizardofoz.dataStore
import com.stockinos.mobile.wizardofoz.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthManager(private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val NAME_KEY = stringPreferencesKey("name")
        private val PHONE_NUMBER_KEY = stringPreferencesKey("phone_number")
    }

    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit{ preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    fun getUser(): Flow<User> {
        return context.dataStore.data.map { preferences ->
            User(
                name = preferences[NAME_KEY]?:"",
                phoneNumber = preferences[PHONE_NUMBER_KEY]?:""
            )
        }
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit{ preferences ->
            preferences[NAME_KEY] = user.name
            preferences[PHONE_NUMBER_KEY] = user.phoneNumber
        }
    }

    suspend fun deleteUser() {
        context.dataStore.edit { preferences ->
            preferences.remove(NAME_KEY)
            preferences.remove(PHONE_NUMBER_KEY)
        }
    }
}