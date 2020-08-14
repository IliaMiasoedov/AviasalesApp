package ilya.myasoedov.aviasales.features.presentation.map

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener
import com.google.android.gms.maps.model.LatLng
import ilya.myasoedov.aviasales.util.Vector
import kotlin.math.absoluteValue

private const val ANIMATION_DURATION = 8000L // in mills
private const val DEGREES_180 = 180f
private const val DEGREES_360 = 360f

class PlaneAnimationController(
    private val onChangeVectorListener: (Vector) -> Unit
) {
    private var state = State.INIT
    private var animator: ValueAnimator? = null

    fun launch(pointsList: Array<Vector>) {
        if (state == State.INIT) {
            state = State.PROGRESS

            animator = createAnimator(pointsList)
            animator?.start()
        }
    }

    fun forceLaunch(pointsList: Array<Vector>) {
        reset()
        launch(pointsList)
    }

    private fun createAnimator(pointsList: Array<Vector>): ValueAnimator {
        return ValueAnimator().apply {
            setObjectValues(*pointsList)
            setEvaluator(VectorEvaluator())
            duration = ANIMATION_DURATION
            addUpdateListener(this@PlaneAnimationController::processUpdateAction)
            interpolator = LinearInterpolator()
            addListener(onEnd = {
                state = State.COMPLETED
            })
        }
    }

    private fun reset() {
        animator?.cancel()
        state = State.INIT
        animator = null
    }

    private fun processUpdateAction(v: ValueAnimator) {
        onChangeVectorListener(v.animatedValue as Vector)
    }

    private fun computeRotation(t: Float, start: Float, end: Float): Float {
        val diff = DEGREES_360 - (start - end).absoluteValue
        return if (start < 0 && end > 0 && (start - end).absoluteValue > DEGREES_180) {
            if (t >= 0 && t <= (DEGREES_180 + start).absoluteValue / diff) {
                start - t * diff
            } else {
                DEGREES_180 - t * diff
            }
        } else if (start > 0 && end < 0 && (start - end).absoluteValue > DEGREES_180) {
            if (t >= 0 && t < (DEGREES_180 - start).absoluteValue / diff) {
                start + t * diff
            } else {
                -DEGREES_180 - t * diff
            }
        } else {
            (1 - t) * start + t * end
        }
    }

    private inner class VectorEvaluator : TypeEvaluator<Vector> {
        override fun evaluate(t: Float, startValue: Vector, endValue: Vector): Vector {
            val lat = (1 - t) * startValue.point.latitude + t * endValue.point.latitude
            val lon = (1 - t) * startValue.point.longitude + t * endValue.point.longitude
            return Vector(
                LatLng(lat, lon),
                computeRotation(t, startValue.heading, endValue.heading)
            )
        }
    }
}

enum class State {
    INIT,
    PROGRESS,
    COMPLETED
}
