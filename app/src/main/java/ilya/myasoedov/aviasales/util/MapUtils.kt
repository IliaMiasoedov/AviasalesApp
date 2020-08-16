package ilya.myasoedov.aviasales.util

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlin.math.PI
import kotlin.math.sin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

private const val POINTS_NUMBER = 200

@Suppress("RemoveExplicitTypeArguments")
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun computeDistanceAndVectorsList(from: LatLng, to: LatLng): Pair<List<Vector>, Double> {
    return withContext(Dispatchers.IO) {
        suspendCancellableCoroutine<Pair<List<Vector>, Double>> { continuation ->
            val result = mutableListOf<Vector>()
            val distance = SphericalUtil.computeDistanceBetween(from, to)
            val heading = SphericalUtil.computeHeading(from, to)
            val latStep = (to.latitude - from.latitude) / POINTS_NUMBER
            val lonStep = (to.longitude - from.longitude) / POINTS_NUMBER

            var prevPoint: LatLng? = null

            for (index in 0 until POINTS_NUMBER) {
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
                prevPoint?.let {
                    result.add(
                        Vector(it, SphericalUtil.computeHeading(it, offsetPoint).toFloat())
                    )
                }
                prevPoint = offsetPoint
            }
            prevPoint?.let {
                val h = SphericalUtil.computeHeading(it, to).toFloat()
                val list = listOf(Vector(it, h), Vector(to, h))
                result.addAll(list)
            }

            continuation.resume(Pair(result, distance)) {
                return@resume
            }
        }
    }
}

data class Vector(
    val point: LatLng,
    val heading: Float
)
