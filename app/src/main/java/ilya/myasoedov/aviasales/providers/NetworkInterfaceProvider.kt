package ilya.myasoedov.aviasales.providers

import ilya.myasoedov.aviasales.features.data.datasources.ICity
import javax.inject.Inject

class NetworkInterfaceProvider @Inject constructor(retrofitProvider: RetrofitProvider) {

    private val cityImpl = retrofitProvider.getRetrofit().create(ICity::class.java)

    fun getCityInterface(): ICity = cityImpl
}
