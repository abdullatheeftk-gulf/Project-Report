package com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.navigation.LedgerReportNavGraph
import com.gulfappdeveloper.projectreport.root.RootViewModel

@Composable
fun LedgerReportMainScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    hideKeyboard:()->Unit,
    accountType:String = "Customer",
    ledgerReportViewModel: LedgerReportViewModel = hiltViewModel()
) {
    val ledgerReportNavHostController = rememberNavController()

    ledgerReportViewModel.getCustomerForLedger(selectedAccountType = accountType)

    LedgerReportNavGraph(
        ledgerReportNavHostController = ledgerReportNavHostController,
        rootViewModel =rootViewModel ,
        ledgerReportViewModel =ledgerReportViewModel ,
        navHostController = navHostController,
        hideKeyboard = hideKeyboard
    )
}