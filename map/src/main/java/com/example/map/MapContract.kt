package com.example.map

interface MapContract {
    abstract val hasRowGeoPermission: Boolean

    abstract val hasFineGeoPermission: Boolean

    fun getGeoPermissions()

    fun getGeo()

    interface Handler {

        fun setGeo(latitude: Double, longitude: Double)
        fun setGeoOtherPeople()


    }
}