package com.gulfappdeveloper.projectreport.domain.models.login_and_register

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val status: Boolean,
    val message: String,
    val userId: Int,
    val companyId: Int
)