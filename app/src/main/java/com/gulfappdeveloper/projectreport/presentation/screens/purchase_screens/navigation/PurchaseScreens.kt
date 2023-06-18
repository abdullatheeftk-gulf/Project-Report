package com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.navigation

sealed class PurchaseScreens(val route:String){
    object PurchaseHomeScreen:PurchaseScreens("purchase_home_screen")
    object QueryPurchaseMastersReportScreen:PurchaseScreens("query_purchase_masters_screen")
    object PurchaseMastersReportScreen:PurchaseScreens("purchase_masters_report_screen")
    object QuerySupplierPurchaseReportScreen:PurchaseScreens("query_supplier_purchase_report_screen")
    object SupplierPurchaseReportScreen:PurchaseScreens("supplier_purchase_report_screen")
    object QuerySupplierLedgerReportScreen:PurchaseScreens("query_supplier_ledger_report_screen")
    object SupplierLedgerReportScreen:PurchaseScreens("supplier_ledger_report_screen")
}
