package com.example.map_repository

import com.example.firebaseroot.FirebasePlugin
import com.example.responce_models.OtherPeopleGeo

object MapRepositoryPlugin {
    private val firebaseDataBaseRepository = FirebasePlugin.getFirebaseDataBaseRepository()

    private val mapDataBaseRepository by lazy {
        object : MapDataBaseRepository {

            override fun uploadGeo(latitude: Double, longitude: Double) =
                firebaseDataBaseRepository.uploadGeo(latitude, longitude)



            override fun getOthersUsers(): List<OtherPeopleGeo> = firebaseDataBaseRepository.getOthersUsers()

        }
    }

    fun getMapRepository(): MapDataBaseRepository = mapDataBaseRepository
}