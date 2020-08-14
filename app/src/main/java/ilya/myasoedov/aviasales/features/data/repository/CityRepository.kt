package ilya.myasoedov.aviasales.features.data.repository

import ilya.myasoedov.aviasales.features.data.datasources.Client
import ilya.myasoedov.aviasales.features.domain.entity.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityRepository(
    private val client: Client
) : ICityRepository {

    @Suppress("RemoveExplicitTypeArguments")
    override suspend fun getCity(from: String, to: String): Result<Pair<City, City>> {
        return withContext(Dispatchers.IO) {
            try {
                val fromCity = client.getCity(query = from).cities.firstOrNull()
                    ?: return@withContext Result.failure<Pair<City, City>>(
                        IncorrectDepartureCityException()
                    )
                val toCity = client.getCity(query = to).cities.firstOrNull()
                    ?: return@withContext Result.failure<Pair<City, City>>(
                        IncorrectArrivalCityException()
                    )
                if (fromCity == toCity) {
                    return@withContext Result.failure<Pair<City, City>>(TheSameCitiesException())
                }
                Result.success(Pair(fromCity.toDomain(), toCity.toDomain()))
            } catch (e: Exception) {
                Result.failure<Pair<City, City>>(e)
            }
        }
    }

    @Suppress("RemoveExplicitTypeArguments")
    override suspend fun getCitySuggestions(query: String): Result<List<String>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = client.getCity(query)
                Result.success(result.cities.map { it.toDomain().city })
            } catch (e: Exception) {
                Result.failure<List<String>>(e)
            }
        }
    }
}

interface ICityRepository {

    suspend fun getCity(from: String, to: String): Result<Pair<City, City>>

    suspend fun getCitySuggestions(query: String): Result<List<String>>
}

class IncorrectDepartureCityException : Throwable("Incorrect departure city")

class IncorrectArrivalCityException : Throwable("Incorrect arrival city")

class TheSameCitiesException : Throwable("The same cities")
