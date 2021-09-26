package com.tustar.demo.util

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.tustar.demo.DemoPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


object DemoPrefsSerializer : Serializer<DemoPrefs> {
    override val defaultValue: DemoPrefs = DemoPrefs.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): DemoPrefs {
        try {
            return DemoPrefs.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: DemoPrefs, output: OutputStream) = t.writeTo(output)
}

val Context.demoPrefs: DataStore<DemoPrefs> by dataStore(
    fileName = "demo_prefs.pb",
    serializer = DemoPrefsSerializer
)


suspend fun doNotShowFlow(context: Context): Flow<DemoPrefs> = context.demoPrefs.data
    .catch { exception ->
        // dataStore.data throws an IOException when an error is encountered when reading data
        if (exception is IOException) {
            Logger.e("Error reading demo preferences.", exception)
            emit(DemoPrefs.getDefaultInstance())
        } else {
            throw exception
        }
    }
    .map { it }

suspend fun updateDoNotShow(context: Context, doNotShow: Boolean) =
    context.demoPrefs.updateData { prefs ->
        prefs.toBuilder().setDoNotShow(doNotShow).build()
    }