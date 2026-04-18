package com.terabyte.data.storage.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(
        name = "preferencesDataStore",
    )

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_API_TOKEN] = token
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_API_TOKEN]
        }.first()
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_API_TOKEN)
        }
    }

    companion object {
        private val KEY_API_TOKEN = stringPreferencesKey("apiToken")
    }
}