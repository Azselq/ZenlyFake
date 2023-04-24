package com.example.firebaseroot

import com.example.responce_models.AuthState
import io.reactivex.rxjava3.core.Observable

interface FirebaseAuthRepository {

    fun getStateObservable(): Observable<AuthState>

    fun authWithEmailAddPassword(email: String, password: String)

    fun exit()
}