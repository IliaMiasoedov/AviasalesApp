package ilya.myasoedov.aviasales.features.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val lat: Double,
    val lon: Double
) : Parcelable
