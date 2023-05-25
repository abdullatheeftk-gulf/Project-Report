package com.gulfappdeveloper.projectreport.domain.models.ledger

import kotlinx.serialization.Serializable

@Serializable
data class GetCustomerForLedgerReportResponse(
    val accountId: Int,
    val accountType: String,
    val address: String,
    val companyId: Int,
    val id: Int,
    val name: String,
    val phone: String,
    val place: String
)