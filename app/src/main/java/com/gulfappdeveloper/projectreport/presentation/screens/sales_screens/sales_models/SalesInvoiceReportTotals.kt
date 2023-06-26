package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models

data class SalesInvoiceReportTotals(
    val sumOfTaxable:Double,
    val sumOfTax:Double,
    val sumOfReturn:Double,
    val sumOfTaxOnReturn:Double,
    val sumOfNet:Double
)
