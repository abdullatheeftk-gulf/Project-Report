package com.gulfappdeveloper.projectreport.domain.models.sales


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaleSummariesResponse(
    val returnTax: Float,
    val date: String,

    val taxable: Float,

    val tax: Float,


    val returnTaxable: Float,



    val net: Float
)