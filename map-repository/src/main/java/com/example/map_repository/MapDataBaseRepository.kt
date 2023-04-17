package com.example.map_repository

import com.example.responce_models.OtherPeopleGeo

interface MapDataBaseRepository {
    fun uploadGeo( latitude: Double, longitude: Double)

    fun getOthersUsers(): List<OtherPeopleGeo>
}