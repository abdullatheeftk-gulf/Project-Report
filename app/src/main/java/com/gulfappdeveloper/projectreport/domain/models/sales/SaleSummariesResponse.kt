package com.gulfappdeveloper.projectreport.domain.models.sales


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaleSummariesResponse(
    @SerialName("date")
    val date: String,
    @SerialName("taxable")
    val taxable: Float,
    @SerialName("tax")
    val tax: Float,
    @SerialName("returnTaxable")
    val returnTaxable: Float,
    @SerialName("returnTax")
    val returnTax: Float,
    @SerialName("net")
    val net: Float
)