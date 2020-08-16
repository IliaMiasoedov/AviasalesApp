package ilya.myasoedov.aviasales.features.domain.usecase

import ilya.myasoedov.aviasales.features.data.repository.ICityRepository

class GetCitySuggestionsUseCase(
    private val cityRepository: ICityRepository
) : IGetCitySuggestionsUseCase {

    override suspend fun getCitySuggestions(query: String): Result<List<String>> {
        return cityRepository.getCitySuggestions(query)
    }
}

interface IGetCitySuggestionsUseCase {
    suspend fun getCitySuggestions(query: String): Result<List<String>>
}
