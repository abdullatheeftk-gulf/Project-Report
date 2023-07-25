package com.gulfappdeveloper.projectreport.domain.models.purchase

import kotlinx.serialization.Serializable

@Serializable
data class PurchaseSummaryResponse(
    val invoiceDate: String,
    val invoiceNo: Int,
    val taxable: Float,
    val tax: Float,
    val net: Float,
    val supplier: String?,
    val taxId: String?
)