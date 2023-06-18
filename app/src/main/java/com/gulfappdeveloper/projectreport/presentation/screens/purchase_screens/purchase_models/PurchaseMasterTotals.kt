package com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models

data class PurchaseMasterTotals(
    val sumOfTaxable:Double,
    val sumOfTax: Double,
    val sumOfNet:Double,
    val sumOfPayment:Double,
    val sumOfReturnAmount:Double,
    val sumOfBalanceAmount:Double
)
