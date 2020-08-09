package ilya.myasoedov.aviasales.features.data.datasources

import ilya.myasoedov.aviasales.features.data.model.CityResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ICity {

    @GET("autocomplete")
    suspend fun getCity(
        @Query("term") query: String,
        @Query("lang") lang: String = "ru"
    ): CityResponse
}
