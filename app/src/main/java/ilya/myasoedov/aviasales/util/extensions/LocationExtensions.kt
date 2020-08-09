package ilya.myasoedov.aviasales.util.extensions

import com.google.android.gms.maps.model.LatLng
import ilya.myasoedov.aviasales.features.domain.entity.Location

fun Location.toLatLng(): LatLng = LatLng(lat, lon)
