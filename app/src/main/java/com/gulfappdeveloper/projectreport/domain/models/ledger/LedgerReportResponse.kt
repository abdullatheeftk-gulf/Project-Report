package com.gulfappdeveloper.projectreport.domain.models.ledger

import kotlinx.serialization.Serializable

@Serializable
data class LedgerReportResponse(
    val address: String,
    val balance: Float,
    val details: List<LedgerDetail>,
    val partyName: String,
    val phone: String
)