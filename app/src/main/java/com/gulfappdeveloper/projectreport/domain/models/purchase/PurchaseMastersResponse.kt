package com.gulfappdeveloper.projectreport.domain.models.purchase

import kotlinx.serialization.Serializable

@Serializable
data class PurchaseMastersResponse(
    val invoiceDate: String,
    val invoiceNo: Int,
    val taxable: Float,
    val tax: Float,
    val net: Float,
    val payment: Float,
    val returnAmount: Float,
    val balanceAmount: Float,
    val supplier: String?
)