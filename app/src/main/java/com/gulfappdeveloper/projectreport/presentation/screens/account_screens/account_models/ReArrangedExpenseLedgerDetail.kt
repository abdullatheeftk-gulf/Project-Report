package com.gulfappdeveloper.projectreport.presentation.screens.account_screens.account_models

data class ReArrangedExpenseLedgerDetail(
    val si:Int,
    val voucherDate:String,
    val voucherNo:Int,
    val particulars:String,
    val debit:Float = 0f,
    val credit:Float = 0f
)
