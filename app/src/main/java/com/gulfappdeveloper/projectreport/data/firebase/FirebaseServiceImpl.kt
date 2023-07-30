package com.gulfappdeveloper.projectreport.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.gulfappdeveloper.projectreport.domain.models.firebase.FirebaseError
import com.gulfappdeveloper.projectreport.domain.models.firebase.FirebaseGeneralData
import com.gulfappdeveloper.projectreport.domain.models.firebase.FirebaseLicense
import com.gulfappdeveloper.projectreport.domain.services.FirebaseService
import java.util.Date

class FirebaseServiceImpl(private val fdb: FirebaseFirestore) : FirebaseService {
    override suspend fun insertErrorDataToFireStore(
        collectionName: String,
        documentName: String,
        error: FirebaseError
    ) {
        try {
            fdb.collection(collectionName)
                .document(documentName)
                .set(error)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun insertGeneralData(
        collectionName: String,
        firebaseGeneralData: FirebaseGeneralData
    ) {
        try {
            fdb.collection(collectionName).document(firebaseGeneralData.device + ",${Date()}")
                .set(firebaseGeneralData)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getFirebaseLicense(onGetFirebaseLicense: (FirebaseLicense) -> Unit) {
        try {
            fdb.collection("appLicense").document("unipos").get()
                .addOnSuccessListener {
                    val firebaseLicense =
                        it.toObject(FirebaseLicense::class.java) ?: FirebaseLicense()
                    onGetFirebaseLicense(firebaseLicense)
                }
                .addOnFailureListener {
                    onGetFirebaseLicense(FirebaseLicense())
                }
                .addOnCanceledListener { }
        } catch (e: Exception) {
            e.message
        }
    }
}