package com.gulfappdeveloper.projectreport.domain.models.customer_payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerPaymentResponse(
    val card: String,
    val cash: String,
    val credit: String,
    val date: String,
    val onlineAmount: String,
    val party: String,
    val receiptNo: Int,
    val returnAmount: String,
    val total: String
)