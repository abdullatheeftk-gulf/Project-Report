package com.gulfappdeveloper.projectreport.domain.models.license

@kotlinx.serialization.Serializable
data class LicenseRequestBody(
    val licenseKey: String,
    val macId: String,
    val ipAddress: String,
)
