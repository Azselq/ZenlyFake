package com.example.firebaseroot

import com.example.responce_models.OtherPeopleGeo
import io.reactivex.rxjava3.core.Observable

interface FirebaseDataBaseRepository {

    /** отправить данные на сервер */
    fun uploadGeo( latitude: Double, longitude: Double)

    /** вызов обновления списка юзеров */
    fun subscribeOthersUsersList() // в будущем параметры в поле передашь и сможешь фильтрацию сделать

    /**
     * подписка списка юзеров
     */
    fun getOthersUsersListObservable(): Observable<Pair<List<OtherPeopleGeo>, Throwable?>>

}