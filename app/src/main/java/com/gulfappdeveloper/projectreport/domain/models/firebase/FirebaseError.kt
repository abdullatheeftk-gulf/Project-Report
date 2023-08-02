package com.gulfappdeveloper.projectreport.domain.models.firebase

import androidx.annotation.Keep
import com.gulfappdeveloper.projectreport.BuildConfig
import java.util.*

@Keep
data class FirebaseError(
    val model: String = android.os.Build.MODEL ?: "nil",
    val manufacturer: String = android.os.Build.MANUFACTURER ?: "nil",
    val device: String = android.os.Build.DEVICE ?: "nil",
    val dateAndTime: String = Date().toString(),
    val appVersion:String = BuildConfig.APP_VERSION,
    val time: Long = Date().time,
    val ipAddress: String = "",
    val errorCode: Int = 0,
    val errorMessage: String,
    val url: String = "",
)
