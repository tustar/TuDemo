package com.tustar.demo.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore by preferencesDataStore(name = "settings")

object PreferencesKeys {
    val DO_NO_SHOW = booleanPreferencesKey("do_not_show")
}

data class PermissionPreferences(val doNotShow: Boolean)

suspend fun doNotShowFlow(context: Context) = context.dataStore.data
    .catch { exception ->
        // dataStore.data throws an IOException when an error is encountered when reading data
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val doNotShow = preferences[PreferencesKeys.DO_NO_SHOW] ?: false
        PermissionPreferences(doNotShow)
    }

suspend fun updateDoNotShow(context: Context, doNotShow: Boolean) {
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.DO_NO_SHOW] = doNotShow
    }
}