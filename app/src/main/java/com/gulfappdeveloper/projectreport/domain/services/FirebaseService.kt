package com.gulfappdeveloper.projectreport.domain.services

import com.gulfappdeveloper.projectreport.domain.models.firebase.FirebaseError
import com.gulfappdeveloper.projectreport.domain.models.firebase.FirebaseGeneralData
import com.gulfappdeveloper.projectreport.domain.models.firebase.FirebaseLicense

interface FirebaseService {
    suspend fun insertErrorDataToFireStore(
        collectionName: String,
        documentName: String,
        error: FirebaseError
    )

    suspend fun insertGeneralData(
        collectionName: String,
        firebaseGeneralData: FirebaseGeneralData
    )

    suspend fun getFirebaseLicense(onGetFirebaseLicense:(FirebaseLicense)->Unit)
}