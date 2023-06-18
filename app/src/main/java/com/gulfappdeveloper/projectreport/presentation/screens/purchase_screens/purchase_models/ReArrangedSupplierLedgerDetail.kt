package com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models

data class ReArrangedSupplierLedgerDetail(
    val si:Int,
    val voucherDate:String,
    val voucherNo:Int,
    val particulars:String,
    val debit:Float = 0f,
    val credit:Float = 0f
)
