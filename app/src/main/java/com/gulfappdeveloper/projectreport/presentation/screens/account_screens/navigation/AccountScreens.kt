package com.gulfappdeveloper.projectreport.presentation.screens.account_screens.navigation

sealed class AccountScreens(val route:String){
    object HomeScreen:AccountScreens("account_home_screen")
    object QueryExpenseLedgerScreen:AccountScreens("query_expense_ledger_screen")
    object ExpenseLedgerScreen:AccountScreens("expense_ledger_screen")
    object QueryPaymentsReportScreen:AccountScreens("query_payments_report_screen")
    object PaymentsReportScreen:AccountScreens("payments_report_screen")
    object QueryReceiptsReportScreen:AccountScreens("query_receipt_report_screen")
    object ReceiptsReportScreen:AccountScreens("receipt_report_screen")
}
