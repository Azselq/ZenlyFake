package com.example.auth

import androidx.fragment.app.Fragment
import com.example.firebaseroot.FirebasePlugin

object AuthPlugin {
    fun isUserAuth(): Boolean = FirebasePlugin.isUserAuth()
    fun getAuthFragment(): Fragment = AuthFragment.newInstance()
}