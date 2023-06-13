package com.gulfappdeveloper.projectreport.domain.models.sales

import kotlinx.serialization.Serializable

@Serializable
data class SalesInvoiceResponse(
    val date: String,
    val invoiceNo: Int,
    val party: String?,
    val taxable: Float,
    val tax: Float,
    val returnTaxable: Float,
    val taxOnReturn: Float,
    val net: Float
)