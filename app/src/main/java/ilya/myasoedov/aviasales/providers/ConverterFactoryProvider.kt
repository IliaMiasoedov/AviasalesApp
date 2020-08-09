package ilya.myasoedov.aviasales.providers

import com.google.gson.GsonBuilder
import ilya.myasoedov.aviasales.features.data.datasources.converter.GsonConverterFactory
import retrofit2.Converter

class ConverterFactoryProvider {

    private val converterFactory = GsonConverterFactory(GsonBuilder().setLenient().create())

    fun getConverterFactory(): Converter.Factory = converterFactory
}
