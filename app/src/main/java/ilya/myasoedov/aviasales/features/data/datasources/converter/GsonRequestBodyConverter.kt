package ilya.myasoedov.aviasales.features.data.datasources.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import java.io.OutputStreamWriter
import java.nio.charset.Charset
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Converter

val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")
val UTF_8: Charset = Charset.forName("UTF-8")

@Suppress("SpellCheckingInspection")
class GsonRequestBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) :
    Converter<T, RequestBody> {

    override fun convert(value: T): RequestBody? {
        val buffer = Buffer()
        val writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
        val jsonWriter = gson.newJsonWriter(writer)
        adapter.write(jsonWriter, value)
        jsonWriter.close()
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString())
    }
}
