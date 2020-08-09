package ilya.myasoedov.aviasales.features.data.model

import com.google.gson.annotations.SerializedName
import ilya.myasoedov.aviasales.features.domain.entity.City

data class City(
    val countryCode: String,
    val country: String,
    val latinFullName: String,
    @SerializedName("fullname") val fullName: String,
    val city: String,
    val latinCity: String,
    val location: Location
) {

    fun toDomain(): City = City(location = location.toDomain(), city = city)
}
