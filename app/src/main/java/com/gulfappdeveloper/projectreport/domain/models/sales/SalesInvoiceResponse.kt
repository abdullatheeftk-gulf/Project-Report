package com.gulfappdeveloper.projectreport.domain.models.sales


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SalesInvoiceResponse(
    @SerialName("date")
    val date: String,
    @SerialName("invoiceNo")
    val invoiceNo: Int,
    @SerialName("party")
    val party: String,
    @SerialName("taxable")
    val taxable: Float,
    @SerialName("tax")
    val tax: Float,
    @SerialName("returnTaxable")
    val returnTaxable: Float,
    @SerialName("taxOnReturn")
    val taxOnReturn: Float,
    @SerialName("net")
    val net: Float
)