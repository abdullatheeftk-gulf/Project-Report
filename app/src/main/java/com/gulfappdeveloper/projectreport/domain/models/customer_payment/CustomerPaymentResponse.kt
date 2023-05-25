package com.gulfappdeveloper.projectreport.domain.models.customer_payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerPaymentResponse(
    val card: Double,
    val cash: Double,
    @SerialName("credt")
    val credit: Int,
    val date: String,
    val online: Int,
    val party: String,
    val receiptNo: Int,
    val returnAmount: Int,
    val total: Int
)