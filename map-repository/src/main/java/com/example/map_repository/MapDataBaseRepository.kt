package com.example.map_repository

import com.example.responce_models.OtherPeopleGeo
import io.reactivex.rxjava3.core.Observable

interface MapDataBaseRepository {
    fun uploadGeo( latitude: Double, longitude: Double)

    /** вызов обновления списка юзеров */
    fun subscribeOthersUsersList() // в будущем параметры в поле передашь и сможешь фильтрацию сделать

    /**
     * подписка списка юзеров
     */
    fun getOthersUsersListObservable(): Observable<Pair<List<OtherPeopleGeo>, Throwable?>>
}