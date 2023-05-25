package com.gulfappdeveloper.projectreport.domain.models.license

@kotlinx.serialization.Serializable
data class UniLicenseDetails(
    val licenseKey: String,
    val licenseType: String,
    val expiryDate: String?
)