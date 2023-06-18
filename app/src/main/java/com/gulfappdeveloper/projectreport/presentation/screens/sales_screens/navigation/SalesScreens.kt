package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.navigation

sealed class SalesScreens(val route: String) {

    object SalesHomeScreen : SalesScreens("sales_home_screen")

    object QuerySalesInvoiceReportScreen : SalesScreens("query_sales_invoice_screen")
    object SalesInvoiceReportScreen : SalesScreens("sales_invoice_report_screens")

    object QuerySaleSummariesReportScreen : SalesScreens("query_sale_summaries_report_screen")
    object SaleSummariesReportScreen : SalesScreens("sale_summaries_report_screens")

    object QueryUserSalesReport : SalesScreens("query_user_sales_report_screen")
    object UserSalesReportScreen : SalesScreens("user_sales_report_screen")

    object QueryCustomerLedgerReportScreen : SalesScreens("query_customer_ledger_report_screen")
    object CustomerLedgerReportScreen : SalesScreens(route = "customer_ledger_report_screen")

    object QueryCustomerPaymentReportScreen : SalesScreens("query_customer_payment_report_screen")
    object CustomerPaymentReportScreen : SalesScreens("customer_payment_report_screen")

    object QueryPosPaymentReportScreen : SalesScreens("query_pos_payment_report_screen")
    object PosPaymentReportScreen : SalesScreens("pos_payment_report_screen")
}
