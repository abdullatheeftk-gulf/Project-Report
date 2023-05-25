package com.gulfappdeveloper.projectreport.domain.models.login_and_register

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val companyId: Int,
    val id: Int,
    val isActive: Boolean,
    val password: String,
    val userId: Int,
    val userName: String
)