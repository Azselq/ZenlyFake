package com.example.firebaseroot

import android.util.Log
import com.example.responce_models.OtherPeopleGeo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

internal class FirebaseDataBaseRepositoryImpl : FirebaseDataBaseRepository {

    private val firestore = Firebase.firestore
    private val otherPeopleGeoSubject = BehaviorSubject.create<Pair<List<OtherPeopleGeo>, Throwable?>>()

    override fun uploadGeo(latitude: Double, longitude: Double) {
        firestore.collection("UsersGeo").document(FirebasePlugin.getUserId()).set(
            hashMapOf("latitude" to latitude, "longitude" to longitude, "name" to FirebasePlugin.getUserId())
        ).addOnSuccessListener { documentReference ->
            Log.d("checkResult", "uploadGeo: успешно добавлено")
        }
            .addOnFailureListener { e ->
                Log.d("checkResult", "uploadGeo: $e")
            }
    }

    override fun subscribeOthersUsersList() {
        firestore.collection("UsersGeo").get().addOnSuccessListener {
            val userList = it.map { user -> user.toObject(OtherPeopleGeo::class.java) }
            // вот тут поставь отлов ошибок=)
            otherPeopleGeoSubject.onNext(
                Pair(userList, null)
            )
        }
    }

    override fun getOthersUsersListObservable(): Observable<Pair<List<OtherPeopleGeo>, Throwable?>> = otherPeopleGeoSubject
}


