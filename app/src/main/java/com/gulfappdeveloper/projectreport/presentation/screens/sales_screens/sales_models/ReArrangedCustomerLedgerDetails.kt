package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models

data class ReArrangedCustomerLedgerDetails(
    val si:Int,
    val voucherDate:String,
    val voucherNo:Int,
    val particulars:String,
    val debit:Float = 0f,
    val credit:Float = 0f
)
