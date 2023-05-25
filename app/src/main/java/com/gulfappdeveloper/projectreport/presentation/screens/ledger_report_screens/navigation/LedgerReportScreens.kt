package com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.navigation

sealed class LedgerReportScreens(val route:String){
    object QueryLedgerScreen:LedgerReportScreens("query_ledger_screen")
    object LedgerReportScreen:LedgerReportScreens("ledger_report_screen")
}
