package com.example.settings

interface SettingsContract {
    fun openAuthScreen()
    fun buttonSelector()
    interface Handler {

        fun closeBanner()

    }
}