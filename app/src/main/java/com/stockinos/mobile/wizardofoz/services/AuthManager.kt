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
        private val ID_KEY = stringPreferencesKey("id")
        private val NAME_KEY = stringPreferencesKey("name")
        private val PHONE_NUMBER_KEY = stringPreferencesKey("phone_number")
        private val TYPE_KEY = stringPreferencesKey("type")
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
                id = (preferences[ID_KEY]?: -1) as Int,
                name = preferences[NAME_KEY]?:"",
                phoneNumber = preferences[PHONE_NUMBER_KEY]?:"",
                type = preferences[TYPE_KEY]?:"",
            )
        }
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit{ preferences ->
            preferences[ID_KEY] = user.id.toString()
            preferences[NAME_KEY] = user.name
            preferences[PHONE_NUMBER_KEY] = user.phoneNumber
            preferences[TYPE_KEY] = user.type
        }
    }

    suspend fun deleteUser() {
        context.dataStore.edit { preferences ->
            preferences.remove(NAME_KEY)
            preferences.remove(PHONE_NUMBER_KEY)
        }
    }
}