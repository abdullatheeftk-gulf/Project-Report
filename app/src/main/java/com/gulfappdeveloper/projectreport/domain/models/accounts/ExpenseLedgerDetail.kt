package com.gulfappdeveloper.projectreport.domain.models.accounts

import kotlinx.serialization.Serializable

@Serializable
data class ExpenseLedgerDetail(
    val vchrDate: String,
    val vchrType: String,
    val vchrNo: Int,
    val particulars: String,
    val amount: Float
)