package ilya.myasoedov.aviasales.providers

import android.content.Context
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import okhttp3.Cache
import okhttp3.OkHttpClient

private const val TIMEOUT = 15L
private const val WRITE_TIMEOUT = 20L
private const val CONNECT_TIMEOUT = 20L
private const val CACHE_SIZE: Long = 10 * 1024 * 1024

class OkHttpClientProvider @Inject constructor(
    context: Context,
    cacheInterceptorProvider: CacheInterceptorProvider
) {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .cache(Cache(context.cacheDir, CACHE_SIZE))
        .addInterceptor(cacheInterceptorProvider.getCacheInterceptor())
        .build()

    fun getOkHttpClient(): OkHttpClient = okHttpClient
}
