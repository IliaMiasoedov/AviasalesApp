package ilya.myasoedov.aviasales.features.data.datasources

import ilya.myasoedov.aviasales.features.data.model.CityResponse
import ilya.myasoedov.aviasales.providers.NetworkInterfaceProvider
import javax.inject.Inject

class Client @Inject constructor(networkInterfaceProvider: NetworkInterfaceProvider) {

    private val cityInterface = networkInterfaceProvider.getCityInterface()

    suspend fun getCity(query: String): CityResponse {
        return cityInterface.getCity(query = query)
    }
}
