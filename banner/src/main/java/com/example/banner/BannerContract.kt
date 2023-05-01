package com.example.banner

interface BannerContract {

    fun openAuthScreen()

    fun getGeoPermissions()


    interface Handler {

        fun closeBanner()

    }
}