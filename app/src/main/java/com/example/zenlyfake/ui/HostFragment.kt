package com.example.zenlyfake.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.auth.AuthPlugin
import com.example.banner.BannerFragment
import com.example.banner.BannerPlugin
import com.example.fakezenly.ui.HostViewModel
import com.example.map.MapContract
import com.example.map.MapPlugin
import com.example.map_repository.MapRepositoryPlugin
import com.example.responce_models.OtherPeopleGeo
import com.example.zenlyfake.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HostFragment : Fragment(), MapContract {

    override val hasRowGeoPermission: Boolean
        get() = checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)

    override val hasFineGeoPermission: Boolean
        get() = checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)

    private val geoPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            Log.d("checkResult", "requestPermissionLauncher word isGranted == $isGranted ")
            if (isGranted) {
                getGeo()
            } else {
                /** тут типо логика должна быть -_-*/
                replaceFragment(BannerPlugin.getBannerFragment())
            }
        }

    override fun getGeoPermissions() {
        when {
            hasRowGeoPermission.not() -> geoPermissionLauncher.launch(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            hasFineGeoPermission.not() -> geoPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            else -> getGeo()
        }
    }
    @SuppressLint("MissingPermission")
    override fun getGeo() {
        val locationServices =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
        var location: Location? = null
        /* тут хитрая логика костылей, смотрим всех провайдеров(passive,gps,network) и в обратном порядке просим у них локацию, */
        locationServices.allProviders.reversed().forEach {
            if (location != null) return@forEach
            location = locationServices.getLastKnownLocation(it)
        }
        location?.let {
            (childFragmentManager.fragments.firstOrNull { it is MapContract.Handler } as? MapContract.Handler)?.setGeo(
                it.latitude,
                it.longitude
            )
        }
        (childFragmentManager.fragments.firstOrNull { it is MapContract.Handler } as? MapContract.Handler)?.setGeoOtherPeople(37.42,-122.123)
    }
    private lateinit var viewModel: HostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_host, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HostViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.Default) {
            viewModel.actions.collect {
                when (it) {
                    HostViewModel.Action.OpenAuthScreen -> replaceFragment(AuthPlugin.getAuthFragment())
                    HostViewModel.Action.OpenMainScreen -> replaceFragment(MapPlugin.getMapFragment())
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit()
    }
    private fun checkPermission(permission: String) =
        ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED

    companion object {
        fun newInstance() = HostFragment()
    }
}