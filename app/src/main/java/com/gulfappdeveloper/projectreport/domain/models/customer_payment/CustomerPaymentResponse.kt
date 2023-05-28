package com.gulfappdeveloper.projectreport.domain.models.customer_payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerPaymentResponse(
    val card: Float,
    val cash: Float,
    @SerialName("credt")
    val credit: Float,
    val date: String,
    val online: Float,
    val party: String,
    val receiptNo: Int,
    val returnAmount: Float,
    val total: Float
)