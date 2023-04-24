package com.example.auth

import androidx.lifecycle.ViewModel
import com.example.firebaseroot.FirebaseAuthRepository
import com.example.firebaseroot.FirebasePlugin
import com.example.sharedpreferences.SharedPrefPlugin
import com.example.sharedpreferences.SharedPrefRepository

internal class AuthViewModel() : ViewModel() {

    private val firebaseAuthRepository: FirebaseAuthRepository = FirebasePlugin.getFirebaseAuthRepository()
    private val sharedPrefRepository: SharedPrefRepository = SharedPrefPlugin.getSharedPref()

    //Создано 3 аккаунта (denja-25@mail.ru,19871987),(kekk@mail.ru,kekkek),(lol@mail.ru,kekkek)
    //Для 2 и 3, я поменял геопозицию в бд вручную
    fun logIn(login: String, password: String) {
        firebaseAuthRepository.authWithEmailAddPassword(login, password)

    }

}