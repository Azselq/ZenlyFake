package com.example.auth

import androidx.lifecycle.ViewModel
import com.example.firebaseroot.FirebaseAuthRepository
import com.example.firebaseroot.FirebasePlugin
import com.example.sharedpreferences.SharedPrefPlugin
import com.example.sharedpreferences.SharedPrefRepository

internal class AuthViewModel() : ViewModel() {

    private val firebaseAuthRepository: FirebaseAuthRepository = FirebasePlugin.getFirebaseAuthRepository()
    private val sharedPrefRepository: SharedPrefRepository = SharedPrefPlugin.getSharedPref()

    fun logIn(login: String, password: String) {
        firebaseAuthRepository.authWithEmailAddPassword("kekk@mail.ru", "kekkek")

    }

}