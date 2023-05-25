package com.gulfappdeveloper.projectreport.domain.models.license

@kotlinx.serialization.Serializable
data class LicenseResponse(
    val message: LicenseMessage,
    val status: Int
)
