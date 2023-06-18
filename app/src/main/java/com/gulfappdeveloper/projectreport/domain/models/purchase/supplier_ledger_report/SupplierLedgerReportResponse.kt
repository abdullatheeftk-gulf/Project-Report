package com.gulfappdeveloper.projectreport.domain.models.purchase.supplier_ledger_report

import kotlinx.serialization.Serializable

@Serializable
data class SupplierLedgerReportResponse(
    val partyName: String,
    val balance: Float,
    val address: String,
    val phone: String,
    val taxId: String?,
    val details: List<Detail>
)