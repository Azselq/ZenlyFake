package com.example.sharedpreferences

object SharedPrefPlugin {

    private val sharedPrefRepository by lazy {
        SharedPrefRepositoryImpl()
    }


    fun getSharedPref():SharedPrefRepository = sharedPrefRepository
}