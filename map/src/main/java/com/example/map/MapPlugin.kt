package com.example.map

import androidx.fragment.app.Fragment
import com.yandex.mapkit.MapKitFactory

object MapPlugin {

    private var isMapApiKeyInstalled = false

    fun getMapFragment(): Fragment = MapFragment.newInstance()

    fun setMapApiKey() {
        if (isMapApiKeyInstalled.not()) {
            MapKitFactory.setApiKey("138575a9-f131-44ef-aef8-97d9a48c21bf")
        }
        isMapApiKeyInstalled = true
    }
}