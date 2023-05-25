package com.gulfappdeveloper.projectreport.domain.models.ledger

import kotlinx.serialization.Serializable

@Serializable
data class LedgerDetail(
    val amount: Float,
    val particulars: String,
    val vchrDate: String,
    val vchrNo: Int,
    val vchrType: String
)