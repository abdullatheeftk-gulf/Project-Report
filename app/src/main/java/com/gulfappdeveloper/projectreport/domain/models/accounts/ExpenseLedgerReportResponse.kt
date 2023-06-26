package com.gulfappdeveloper.projectreport.domain.models.accounts

import kotlinx.serialization.Serializable

@Serializable
data class ExpenseLedgerReportResponse(
    val partyName: String,
    val balance: Float,
    val address: String,
    val phone: String,
    val details: List<ExpenseLedgerDetail>
)