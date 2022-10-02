package com.tustar.weather.util

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.tustar.utils.Logger
import com.tustar.weather.WLocation
import com.tustar.weather.WeatherPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object WeatherPrefsSerializer : Serializer<WeatherPrefs> {
    override val defaultValue: WeatherPrefs = WeatherPrefs.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): WeatherPrefs {
        try {
            return WeatherPrefs.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: WeatherPrefs, output: OutputStream) = t.writeTo(output)
}

val Context.weatherPrefs: DataStore<WeatherPrefs> by dataStore(
    fileName = "Weather_prefs.pb",
    serializer = WeatherPrefsSerializer
)

suspend fun weatherPrefsFlow(context: Context): Flow<WeatherPrefs> = context.weatherPrefs.data
    .catch { exception ->
        // dataStore.data throws an IOException when an error is encountered when reading data
        if (exception is IOException) {
            Logger.e("Error reading Weather preferences.", exception)
            emit(WeatherPrefs.getDefaultInstance())
        } else {
            throw exception
        }
    }
    .map { it }

suspend fun updateLocate(context: Context, locate: WLocation) {
    context.weatherPrefs.updateData { prefs ->
        prefs.toBuilder()
            .setLocate(locate)
            .build()
    }
}

suspend fun addCity(context: Context, city: WLocation) {
    context.weatherPrefs.updateData { prefs ->
        prefs.toBuilder()
            .putCities(city.name, city)
            .build()
    }
}

suspend fun removeCity(context: Context, city: WLocation) {
    context.weatherPrefs.updateData { prefs ->
        prefs.toBuilder()
            .removeCities(city.name)
            .build()
    }
}

suspend fun updateMode24H(context: Context, mode24H: WeatherPrefs.Mode) =
    context.weatherPrefs.updateData { prefs ->
        prefs.toBuilder()
            .setMode24H(mode24H)
            .build()
    }

suspend fun updateMode15D(context: Context, mode15D: WeatherPrefs.Mode) =
    context.weatherPrefs.updateData { prefs ->
        prefs.toBuilder()
            .setMode15D(mode15D)
            .build()
    }

suspend fun updateLastUpdated(context: Context, timeMills: Long) =
    context.weatherPrefs.updateData { prefs ->
        prefs.toBuilder()
            .setLastUpdated(timeMills)
            .build()
    }

