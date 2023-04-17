package com.example.banner

import androidx.fragment.app.Fragment

object BannerPlugin {

    fun getBannerFragment(): Fragment = BannerFragment.newInstance()
    var clickButton = false
}