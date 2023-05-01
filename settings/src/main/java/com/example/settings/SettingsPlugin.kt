package com.example.settings

import androidx.fragment.app.Fragment

object SettingsPlugin {
    fun getSettingsFragment(): Fragment = SettingsFragment.newInstance()
}