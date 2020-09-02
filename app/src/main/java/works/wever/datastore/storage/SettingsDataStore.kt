package works.wever.datastore.storage

import android.content.Context
import androidx.datastore.CorruptionException
import androidx.datastore.DataStore
import androidx.datastore.Serializer
import androidx.datastore.createDataStore
import com.google.protobuf.InvalidProtocolBufferException
import wever.works.datastore.Settings
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<Settings> {
    override fun readFrom(input: InputStream): Settings {
        try {
            return Settings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(
        t: Settings,
        output: OutputStream
    ) = t.writeTo(output)
}

class SettingsDataStore(
    val settingsDataStore: DataStore<Settings>
) : DataStore<Settings> by settingsDataStore {

    constructor(context: Context) : this(
        context.createDataStore(
            fileName = "settings.proto",
            serializer = SettingsSerializer
        )
    )
}