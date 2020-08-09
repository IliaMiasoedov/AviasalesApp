package ilya.myasoedov.aviasales.features.data.datasources.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import java.io.IOException
import okhttp3.ResponseBody
import retrofit2.Converter

@Suppress("SpellCheckingInspection")
class GsonResponseBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) :
    Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val jsonReader = gson.newJsonReader(value.charStream())
        value.use {
            return adapter.read(jsonReader)
        }
    }
}
