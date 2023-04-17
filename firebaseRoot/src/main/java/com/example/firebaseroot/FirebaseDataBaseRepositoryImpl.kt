package com.example.firebaseroot

import android.util.Log
import com.example.responce_models.OtherPeopleGeo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

internal class FirebaseDataBaseRepositoryImpl: FirebaseDataBaseRepository {

    private val firestore = Firebase.firestore
    var userList = mutableListOf<OtherPeopleGeo>()
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

    override fun getOthersUsers(): List<OtherPeopleGeo> {
        firestore.collection("UsersGeo").get().addOnSuccessListener {
            for(user in it){
                var data = user.toObject(OtherPeopleGeo::class.java)
                userList.add(data)
                Log.d("getUsers","${data}")
                Log.d("userList","${userList}")
            }
        }
        Log.d("userListBeforeReturn","$userList")
        return userList
      }
}


