package ilya.myasoedov.aviasales.features.domain.usecase

import ilya.myasoedov.aviasales.features.data.repository.ICityRepository
import ilya.myasoedov.aviasales.features.domain.entity.City

class GetCityUseCase(
    private val cityRepository: ICityRepository
) : IGetCityUseCase {

    override suspend fun getCity(from: String, to: String): Result<Pair<City, City>> {
        return cityRepository.getCity(from = from, to = to)
    }
}

interface IGetCityUseCase {

    suspend fun getCity(from: String, to: String): Result<Pair<City, City>>
}
