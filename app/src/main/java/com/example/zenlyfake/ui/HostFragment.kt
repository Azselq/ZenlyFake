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
import com.example.auth.AuthContract
import com.example.auth.AuthFragment
import com.example.auth.AuthPlugin
import com.example.banner.BannerContract
import com.example.banner.BannerFragment
import com.example.banner.BannerPlugin
import com.example.map.MapContract
import com.example.map.MapPlugin
import com.example.settings.SettingsContract
import com.example.settings.SettingsPlugin
import com.example.zenlyfake.R
import com.example.zenlyfake.databinding.FragmentHostBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HostFragment : Fragment(), MapContract, BannerContract, AuthContract, SettingsContract {

    lateinit var binding: FragmentHostBinding

    override val hasRowGeoPermission: Boolean
        get() = checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)

    override val hasFineGeoPermission: Boolean
        get() = checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)

    private lateinit var viewModel: HostViewModel

    private val geoPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            Log.d("checkResult", "requestPermissionLauncher word isGranted == $isGranted ")
            if (isGranted) {
                getGeo()
            } else {
                /** тут типо логика должна быть -_-*/
                binding.navigation.visibility = View.GONE
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
        (childFragmentManager.fragments.firstOrNull { it is BannerContract.Handler } as? BannerContract.Handler)?.closeBanner()
        binding.navigation.visibility = View.VISIBLE
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

        (childFragmentManager.fragments.firstOrNull { it is MapContract.Handler } as? MapContract.Handler)?.setGeoOtherPeople()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHostBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HostViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val childFragmentManager = childFragmentManager
        childFragmentManager.addOnBackStackChangedListener {
            when(childFragmentManager.fragments.lastOrNull()) {
                null,
                is AuthFragment,
                is BannerFragment -> binding.navigation.visibility = View.GONE
                else -> binding.navigation.visibility = View.VISIBLE
            }
        }
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.actions.collect {
                when (it) {
                    HostViewModel.Action.OpenAuthScreen -> {
                        replaceFragment(AuthPlugin.getAuthFragment())
                        binding.navigation.visibility = View.GONE
                    }
                    HostViewModel.Action.OpenMainScreen -> replaceFragment(MapPlugin.getMapFragment())
                }
            }
        }
        binding.navigation.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.map -> replaceFragment(MapPlugin.getMapFragment())
                R.id.settings -> replaceFragment(SettingsPlugin.getSettingsFragment())
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

    override fun openAuthScreen() {
        replaceFragment(AuthPlugin.getAuthFragment())
    }

    override fun openMainScreen() {
        replaceFragment(MapPlugin.getMapFragment())
    }

}