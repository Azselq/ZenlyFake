package com.example.firebaseroot

import com.google.firebase.auth.FirebaseAuth

object FirebasePlugin {

    private val firebaseAuthRepository by lazy {
        FirebaseAuthRepositoryImpl()
    }

    private val firebaseDataBaseRepository by lazy {
        FirebaseDataBaseRepositoryImpl()
    }

    /** Предоставляет имплементацию репозитория [FirebaseAuthRepository] */
    fun getFirebaseAuthRepository(): FirebaseAuthRepository = firebaseAuthRepository

    /** Предоставляет имплементацию репозитория [FirebaseDataBaseRepository] */
    fun getFirebaseDataBaseRepository(): FirebaseDataBaseRepository = firebaseDataBaseRepository

    /** Проверяем зарегистрирован ли юзер */
    fun isUserAuth(): Boolean = FirebaseAuth.getInstance().currentUser != null

    fun getUserId():String = FirebaseAuth.getInstance().currentUser?.uid ?: throw java.lang.IllegalStateException("FirebasePlugin getUserId = null")
}