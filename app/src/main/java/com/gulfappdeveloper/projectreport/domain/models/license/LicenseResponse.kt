package com.gulfappdeveloper.projectreport.domain.models.license

import kotlinx.serialization.Serializable

@Serializable
data class LicenseResponse(
    val message: String,
    val duration: Int,
    val licenseType: String,
    val status: Int,
    val startDate: String,
    val expiryDate: String?
)