package com.example.responce_models

sealed class AuthState {
    object SuccessAuth : AuthState()
    class FailAuth(val error: Throwable) : AuthState()
}