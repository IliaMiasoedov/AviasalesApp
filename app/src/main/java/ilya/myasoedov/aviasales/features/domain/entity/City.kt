package ilya.myasoedov.aviasales.features.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val city: String,
    val location: Location
) : Parcelable
