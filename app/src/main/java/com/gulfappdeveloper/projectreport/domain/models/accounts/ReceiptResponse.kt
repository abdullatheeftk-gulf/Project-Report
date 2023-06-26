package com.gulfappdeveloper.projectreport.domain.models.accounts

import kotlinx.serialization.Serializable

@Serializable
data class ReceiptResponse(
    val date: String,
    val vchrNo: Int,
    val particulars: String,
    val amount: Float
)