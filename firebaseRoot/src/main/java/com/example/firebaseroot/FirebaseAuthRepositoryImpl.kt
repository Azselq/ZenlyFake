package com.example.firebaseroot

import android.content.SharedPreferences
import com.example.responce_models.AuthState
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

internal class FirebaseAuthRepositoryImpl : FirebaseAuthRepository {

    private val authStateSubject = PublishSubject.create<AuthState>()

    override fun getStateObservable(): Observable<AuthState> = authStateSubject

    override fun authWithEmailAddPassword(email: String, password: String) {
        logIn(email, password)
    }


    private fun logIn(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    authStateSubject.onNext(AuthState.SuccessAuth)
                } else {
                    createAccount(email, password)
                }
            }
    }

    private fun createAccount(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    authStateSubject.onNext(AuthState.SuccessAuth)
                } else {
                    authStateSubject.onNext(AuthState.FailAuth(error = task.exception ?: UnknownError()))
                }
            }
    }

}