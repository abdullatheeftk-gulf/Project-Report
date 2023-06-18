package com.gulfappdeveloper.projectreport.domain.models.sales

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PosPaymentResponse(
    val date: String,
    val invoiceNo: Int,
    val party: String?,
    val cash: Float,
    val card: Float,
    val credit: Float,
    @SerialName("returnAmont")
    val returnAmount: Float,
    val total: Float,
    val onlineAmount: Float
)