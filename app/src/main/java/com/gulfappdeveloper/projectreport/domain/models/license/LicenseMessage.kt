package com.gulfappdeveloper.projectreport.domain.models.license

@kotlinx.serialization.Serializable
data class LicenseMessage(
    val message: String,
    val duration: Int,
    val licenseType: String,
    val status: Int,
    val startDate: String,
    val expiryDate: String?,
)