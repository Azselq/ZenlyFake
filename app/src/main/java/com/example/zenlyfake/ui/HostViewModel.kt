package com.example.fakezenly.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.AuthPlugin
import com.example.banner.BannerPlugin
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class HostViewModel : ViewModel() {

    private val _actions = MutableSharedFlow<Action>(replay = 1)
    val actions: SharedFlow<Action> = _actions

    init {
        viewModelScope.launch {
            _actions.emit(
                if (AuthPlugin.isUserAuth()) {
                    Action.OpenMainScreen
                } else {
                    Action.OpenAuthScreen
                }
            )
        }

    }




    sealed class Action {
        object OpenAuthScreen : Action()
        object OpenMainScreen : Action()
    }
}