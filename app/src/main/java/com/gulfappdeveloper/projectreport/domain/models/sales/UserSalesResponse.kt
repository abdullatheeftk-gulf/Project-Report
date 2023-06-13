package com.gulfappdeveloper.projectreport.domain.models.sales

import kotlinx.serialization.Serializable

@Serializable
data class UserSalesResponse(
    val userName: String,
    val salesAmount: Float
)