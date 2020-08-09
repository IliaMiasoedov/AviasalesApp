package ilya.myasoedov.aviasales.features.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import ilya.myasoedov.aviasales.features.domain.entity.City
import ilya.myasoedov.aviasales.util.SingleLiveEvent
import ilya.myasoedov.aviasales.util.computeDistanceAndPointsList
import ilya.myasoedov.aviasales.util.extensions.toLatLng
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * The earth's radius, in meters.
 * Mean radius as defined by IUGG.
 */
private const val EARTH_RADIUS = 6371009

class MapFragmentViewModel @Inject constructor(
    val data: Pair<City, City>
) : ViewModel() {

    private val pointsData = MutableLiveData<List<LatLng>>()
    private val planePositionData = MutableLiveData<LatLng>()
    private val planeRotationData = MutableLiveData<Float>()
    private val isBigRouteData = MutableLiveData<Boolean>()

    private val planeAnimationController = PlaneAnimationController(
        onPositionListener = { planePositionData.value = it },
        onRotationListener = { planeRotationData.value = it }
    )

    val pointsLiveData: LiveData<List<LatLng>>
        get() = pointsData
    val planePositionLiveData: LiveData<LatLng>
        get() = planePositionData
    val planeRotationLiveData: LiveData<Float>
        get() = planeRotationData
    val isBigRouteLiveData: LiveData<Boolean>
        get() = isBigRouteData

    init {
        viewModelScope.launch {
            val result = computeDistanceAndPointsList(
                from = data.first.location.toLatLng(),
                to = data.second.location.toLatLng()
            )
            val list = result.first
            val distance = result.second

            pointsData.value = list
            isBigRouteData.value = distance > EARTH_RADIUS
        }
    }

    fun launchAnimationIfNeeded() {
        val points = pointsData.value
        if (!points.isNullOrEmpty()) {
            planeAnimationController.launch(points.toTypedArray())
        }
    }

    fun forceLaunchAnimation() {
        val points = pointsData.value
        if (!points.isNullOrEmpty()) {
            planeAnimationController.forceLaunch(points.toTypedArray())
        }
    }
}
