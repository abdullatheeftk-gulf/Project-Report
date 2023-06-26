package com.gulfappdeveloper.projectreport.domain.models.accounts

import kotlinx.serialization.Serializable

@Serializable
data class PaymentResponse(
    val date: String,
    val vchrNo: Int,
    val particulars: String,
    val amount: Float
)