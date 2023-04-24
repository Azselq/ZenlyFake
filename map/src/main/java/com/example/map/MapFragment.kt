package com.example.map

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.map.databinding.FragmentMapBinding
import com.example.map_repository.MapRepositoryPlugin
import com.example.responce_models.OtherPeopleGeo
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.bindings.MapHandler
import com.yandex.runtime.image.ImageProvider


class MapFragment : Fragment(), MapContract.Handler {
    private lateinit var binding: FragmentMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapPlugin.setMapApiKey()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MapKitFactory.initialize(requireContext())
    }

    override fun onStart() {
        super.onStart()
        binding.map.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as? MapContract)?.getGeoPermissions()
    }

    override fun onStop() {
        binding.map.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    companion object {
        fun newInstance() = MapFragment()
    }

    override fun setGeo(latitude: Double, longitude: Double) {
        binding.map.map.apply {
            move(
                CameraPosition(
                    Point(latitude, longitude), 11f, 0f, 0f
                )
            )
            mapObjects.addPlacemark(
                Point(latitude, longitude),
                ImageProvider.fromBitmap(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_gpp_good_24)
                        ?.toBitmap()
                )
            )
        }
        MapRepositoryPlugin.getMapRepository().uploadGeo(latitude, longitude)
    }

    override fun setGeoOtherPeople(latitude: Double, longitude: Double) {
        val otherPeople = mutableListOf<List<OtherPeopleGeo>>()
        MapRepositoryPlugin.getMapRepository().subscribeOthersUsersList()
        MapRepositoryPlugin.getMapRepository().getOthersUsersListObservable().subscribe{
            Log.d("checkResult", "setGeoOtherPeople: $it")
            otherPeople.add(it.first)
            Log.d("123","$otherPeople")

            val points = mutableListOf<Point>()
            otherPeople.forEach { users ->
                users.forEach { user ->
                    val point = user.latitude?.let { it1 -> user.longitude?.let { it2 ->
                        Point(it1,
                            it2
                        )
                    } }
                    if (point != null) {
                        points.add(point)
                    }
                }
            }

            binding.map.map.apply {
                points.forEach { point ->
                    mapObjects.addPlacemark(
                        point,
                        ImageProvider.fromBitmap(
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_gpp_good_24)?.toBitmap()
                        )
                    )
                }
            }
        }
    }

}