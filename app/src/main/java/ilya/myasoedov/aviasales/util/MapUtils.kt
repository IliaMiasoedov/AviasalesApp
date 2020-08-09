package ilya.myasoedov.aviasales.util

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlin.math.PI
import kotlin.math.sin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

private const val POINTS_NUMBER = 30

@Suppress("RemoveExplicitTypeArguments")
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun computeDistanceAndPointsList(from: LatLng, to: LatLng): Pair<List<LatLng>, Double> {
    return withContext(Dispatchers.IO) {
        suspendCancellableCoroutine<Pair<List<LatLng>, Double>> { continuation ->
            val result = mutableListOf<LatLng>()
            val distance = SphericalUtil.computeDistanceBetween(from, to)
            val heading = SphericalUtil.computeHeading(from, to)

            for (index in 0 until POINTS_NUMBER) {
                val latStep = (to.latitude - from.latitude) / POINTS_NUMBER
                val lonStep = (to.longitude - from.longitude) / POINTS_NUMBER
                val point =
                    LatLng(
                        from.latitude + index * latStep,
                        from.longitude + index * lonStep
                    )
                val x = SphericalUtil.computeDistanceBetween(from, point)
                val offsetPoint = SphericalUtil.computeOffset(
                    point,
                    distance / 8 * sin(2 * PI * x / distance),
                    heading + 90
                )
                result.add(offsetPoint)
            }
            result.add(to)

            continuation.resume(Pair(result, distance)) {
                return@resume
            }
        }
    }
}
