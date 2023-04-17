package com.example.sharedpreferences

import android.content.Context

interface SharedPrefRepository {
    fun saveInf(email:String, context: Context)
}