package com.gulfappdeveloper.projectreport.domain.models.login_and_register

import kotlinx.serialization.Serializable

@Serializable
data class CompanyRegisterResponse(
    val id: Int,
    val name: String,
    val place: String,
    val taxId: String
)