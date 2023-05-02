package com.example.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseroot.FirebaseAuthRepository
import com.example.firebaseroot.FirebasePlugin
import com.example.responce_models.AuthState
import com.example.sharedpreferences.SharedPrefPlugin
import com.example.sharedpreferences.SharedPrefRepository
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

internal class AuthViewModel() : ViewModel() {

    private val firebaseAuthRepository: FirebaseAuthRepository = FirebasePlugin.getFirebaseAuthRepository()
    private val sharedPrefRepository: SharedPrefRepository = SharedPrefPlugin.getSharedPref()
    private var disposable: Disposable? = null

    private val _actions = MutableSharedFlow<Action>(replay = 1)
    val actions: SharedFlow<Action> = _actions

    init {
        disposable = firebaseAuthRepository.getStateObservable().subscribe {
            when (it) {
                is AuthState.FailAuth -> Log.d("checkResult", "fail auth")
                AuthState.SuccessAuth -> {
                    viewModelScope.launch {
                        _actions.emit(Action.OpenMainScreen)
                    }
                }
            }
        }
    }

    //Создано 3 аккаунта (denja-25@mail.ru,19871987),(kekk@mail.ru,kekkek),(lol@mail.ru,kekkek)
    //Для 2 и 3, я поменял геопозицию в бд вручную
    fun logIn(login: String, password: String) {
        firebaseAuthRepository.authWithEmailAddPassword(login, password)
    }

    override fun onCleared() {
        disposable?.dispose()
        disposable = null
        super.onCleared()
    }

    sealed class Action {
        object OpenMainScreen : Action()
    }
}