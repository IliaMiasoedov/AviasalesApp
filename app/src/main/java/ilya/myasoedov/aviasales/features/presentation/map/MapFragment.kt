package ilya.myasoedov.aviasales.features.presentation.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.ui.IconGenerator
import ilya.myasoedov.aviasales.R
import ilya.myasoedov.aviasales.app.BaseFragment
import ilya.myasoedov.aviasales.features.domain.entity.City
import ilya.myasoedov.aviasales.util.extensions.safePopBackStack
import ilya.myasoedov.aviasales.util.extensions.toLatLng

private const val POLYLINE_GAP_WIDTH = 20f

class MapFragment : BaseFragment<MapFragmentViewModel>(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var toolbar: Toolbar
    private lateinit var planeMarker: Marker
    private lateinit var data: Pair<City, City>

    override fun layoutRes(): Int = R.layout.fragment_map

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = viewModel.data

        toolbar = view.findViewById(R.id.fragment_map_toolbar)

        (childFragmentManager.findFragmentById(R.id.fragment_map_map)
                as SupportMapFragment).getMapAsync(this)

        setListeners()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        setupMap()
        observe()
    }

    private fun observe() {
        viewModel.pointsLiveData.observe(viewLifecycleOwner, Observer { list ->
            drawPolyLine(list)
            viewModel.launchAnimationIfNeeded()
        })
        viewModel.planePositionLiveData.observe(viewLifecycleOwner, Observer { position ->
            planeMarker.position = position
        })
        viewModel.planeRotationLiveData.observe(viewLifecycleOwner, Observer { rotation ->
            planeMarker.rotation = rotation - googleMap.cameraPosition.bearing
        })
        viewModel.isBigRouteLiveData.observe(viewLifecycleOwner, Observer { isBigRoute ->
            if (!isBigRoute) {
                boundMap()
            }
        })
    }

    private fun setListeners() {
        toolbar.setNavigationOnClickListener { findNavController().safePopBackStack() }
        toolbar.setOnMenuItemClickListener {
            viewModel.forceLaunchAnimation()
            true
        }
    }

    private fun setupMap() {
        val dispatcherCity = data.first
        val arrivalCity = data.second

        googleMap.addMarker(createCityMarker(dispatcherCity))
        googleMap.addMarker(createCityMarker(arrivalCity))
        planeMarker = googleMap.addMarker(createPlaneMarker(dispatcherCity.location.toLatLng()))
    }

    private fun boundMap() {
        val departureCityLatLng = data.first.location.toLatLng()
        val arrivalCityLatLng = data.second.location.toLatLng()
        val bounds =
            LatLngBounds.builder().include(departureCityLatLng).include(arrivalCityLatLng).build()

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.2).toInt()

        googleMap.setLatLngBoundsForCameraTarget(bounds)
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding)
        )
        googleMap.setMinZoomPreference(googleMap.cameraPosition.zoom)
    }

    @SuppressLint("InflateParams")
    private fun createCityMarker(city: City): MarkerOptions {
        val markerView = layoutInflater.inflate(R.layout.marker_layout, null)
        markerView.findViewById<TextView>(R.id.marker_text).text = city.city

        val iconGenerator = IconGenerator(context)
        iconGenerator.setContentView(markerView)
        iconGenerator.setBackground(null)
        return MarkerOptions().position(city.location.toLatLng())
            .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon()))
    }

    private fun createPlaneMarker(position: LatLng): MarkerOptions {
        return MarkerOptions().position(position)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_plane))
    }

    private fun drawPolyLine(list: List<LatLng>) {
        val polyLine =
            PolylineOptions().addAll(list)
                .pattern(listOf(Dot(), Gap(POLYLINE_GAP_WIDTH)))
                .jointType(JointType.ROUND)
        context?.let { polyLine.color(ContextCompat.getColor(it, R.color.gray_2)) }
        googleMap.addPolyline(polyLine)
    }
}
