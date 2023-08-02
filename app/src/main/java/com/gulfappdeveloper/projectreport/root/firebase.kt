package com.gulfappdeveloper.projectreport.root

import com.gulfappdeveloper.projectreport.domain.models.firebase.FirebaseError
import com.gulfappdeveloper.projectreport.domain.models.general.Error
import com.gulfappdeveloper.projectreport.domain.services.FirebaseService

suspend fun FirebaseService.sendErrorDataToFirebase(
     url:String = "",
     error:Error,
     funcName:String
 ){
     insertErrorDataToFireStore(
         collectionName = "ErrorData",
         documentName = funcName,
         error = FirebaseError(
             errorCode = error.code,
             errorMessage = error.message ?:"Unknown error",
             url = url
         )

     )

}
