package ilya.myasoedov.aviasales.providers

import ilya.myasoedov.aviasales.BuildConfig
import javax.inject.Inject
import retrofit2.Retrofit

class RetrofitProvider @Inject constructor(
    converterFactoryProvider: ConverterFactoryProvider,
    okHttpClientProvider: OkHttpClientProvider
) {
    private val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(converterFactoryProvider.getConverterFactory())
        .client(okHttpClientProvider.getOkHttpClient()).build()

    fun getRetrofit(): Retrofit = retrofit
}
