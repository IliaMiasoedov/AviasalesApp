package ilya.myasoedov.aviasales.features.data.model

import ilya.myasoedov.aviasales.features.domain.entity.Location

data class Location(
    val lat: Double,
    val lon: Double
) {

    fun toDomain(): Location = Location(lat = lat, lon = lon)
}
