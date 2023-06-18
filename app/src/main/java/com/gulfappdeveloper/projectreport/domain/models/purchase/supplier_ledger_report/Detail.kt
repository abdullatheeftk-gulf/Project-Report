package com.gulfappdeveloper.projectreport.domain.models.purchase.supplier_ledger_report

import kotlinx.serialization.Serializable

@Serializable
data class Detail(
    val vchrDate: String,
    val vchrType: String,
    val vchrNo: Int,
    val particulars: String,
    val amount: Float
)