package com.tustar.ktx

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


suspend fun <T> flow(dataStore: DataStore<Preferences>, key: Preferences.Key<T>, default: T) =
    dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[key] ?: default
        }

suspend fun <T> update(dataStore: DataStore<Preferences>, key: Preferences.Key<T>, value: T) {
    dataStore.edit { preferences ->
        preferences[key] = value
    }
}

suspend fun flowString(
    dataStore: DataStore<Preferences>,
    key: Preferences.Key<String>,
    default: String = "",
) = flow(dataStore, key, default)

suspend fun flowBoolean(
    dataStore: DataStore<Preferences>,
    key: Preferences.Key<Boolean>,
    default: Boolean = false,
) = flow(dataStore, key, default)