package ilya.myasoedov.aviasales.features.presentation.map

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import ilya.myasoedov.aviasales.features.domain.entity.City
import ilya.myasoedov.aviasales.util.Vector
import ilya.myasoedov.aviasales.util.computeDistanceAndVectorsList
import ilya.myasoedov.aviasales.util.extensions.toLatLng
import javax.inject.Inject
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.launch

/**
 * The earth's radius, in meters.
 * Mean radius as defined by IUGG.
 */
private const val EARTH_RADIUS = 6371009

class MapFragmentViewModel @Inject constructor(
    val data: MapFragmentParam
) : ViewModel() {

    private val vectorsData = MutableLiveData<List<Vector>>()
    private val planeVector = MutableLiveData<Vector>()
    private val isBigRouteData = MutableLiveData<Boolean>()
    private val pointsData = Transformations.map(vectorsData) { it.map { v -> v.point } }

    private val planeAnimationController = PlaneAnimationController { planeVector.value = it }

    val pointsLiveData: LiveData<List<LatLng>>
        get() = pointsData
    val planeVectorLiveData: LiveData<Vector>
        get() = planeVector
    val isBigRouteLiveData: LiveData<Boolean>
        get() = isBigRouteData

    init {
        viewModelScope.launch {
            val result = computeDistanceAndVectorsList(
                from = data.from.location.toLatLng(),
                to = data.to.location.toLatLng()
            )
            val list = result.first
            val distance = result.second

            vectorsData.value = list
            isBigRouteData.value = distance > EARTH_RADIUS
        }
    }

    fun launchAnimationIfNeeded() {
        val vectors = vectorsData.value
        if (!vectors.isNullOrEmpty()) {
            planeAnimationController.launch(vectors.toTypedArray())
        }
    }

    fun forceLaunchAnimation() {
        val vectors = vectorsData.value
        if (!vectors.isNullOrEmpty()) {
            planeAnimationController.forceLaunch(vectors.toTypedArray())
        }
    }
}

@Parcelize
data class MapFragmentParam(
    val from: City,
    val to: City
) : Parcelable
