package com.example.sharedpreferences

import android.content.Context

internal class SharedPrefRepositoryImpl: SharedPrefRepository {
    override fun saveInf(email: String, context: Context) {
        var pref = context.getSharedPreferences(email,Context.MODE_PRIVATE)

    }
}