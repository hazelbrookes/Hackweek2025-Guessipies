package com.mobiedev.search.guessipies.network

import android.content.Context
import com.mobiedev.search.guessipies.USERNAME_KEY
import com.mobiedev.search.guessipies.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.also

class UsernameDataStore(
    private val context: Context
) {

    fun usernameFlow(): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USERNAME_KEY]
    }

    suspend fun addUsername(username: String) {
        context.dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[USERNAME_KEY] = username
            }
        }
    }

}