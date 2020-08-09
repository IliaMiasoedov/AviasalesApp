package ilya.myasoedov.aviasales.features.presentation.map

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

private const val ANIMATION_DURATION = 8000L // in mills

class PlaneAnimationController(
    private val onPositionListener: (LatLng) -> Unit,
    private val onRotationListener: (Float) -> Unit
) {
    private var state = State.INIT
    private var position: LatLng? = null
    private var animator: ValueAnimator? = null

    fun launch(pointsList: Array<LatLng>) {
        if (state == State.INIT) {
            state = State.PROGRESS

            animator = createAnimator(pointsList)
            animator?.start()
        }
    }

    fun forceLaunch(pointsList: Array<LatLng>) {
        reset()
        launch(pointsList)
    }

    private fun createAnimator(pointsList: Array<LatLng>): ValueAnimator {
        return ValueAnimator().apply {
            setObjectValues(*pointsList)
            setEvaluator(LatLngEvaluator())
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
        val newPosition = v.animatedValue as LatLng
        position?.let {
            onRotationListener(
                SphericalUtil.computeHeading(it, newPosition).toFloat()
            )
        }
        position = newPosition
        onPositionListener(newPosition)
    }

    private inner class LatLngEvaluator : TypeEvaluator<LatLng> {
        override fun evaluate(t: Float, startValue: LatLng, endValue: LatLng): LatLng {
            val lat = (1 - t) * startValue.latitude + t * endValue.latitude
            val lon = (1 - t) * startValue.longitude + t * endValue.longitude
            return LatLng(lat, lon)
        }
    }
}

enum class State {
    INIT,
    PROGRESS,
    COMPLETED
}
