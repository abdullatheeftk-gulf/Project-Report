package com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.LedgerReportViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.screens.ledger_report_screen.LedgerReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.screens.query_ledger_screen.QueryLedgerScreen
import com.gulfappdeveloper.projectreport.root.RootViewModel

@Composable
fun LedgerReportNavGraph(
    ledgerReportNavHostController:NavHostController,
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    ledgerReportViewModel:LedgerReportViewModel,
    hideKeyboard:()->Unit
) {

    NavHost(
        navController =ledgerReportNavHostController ,
        startDestination = LedgerReportScreens.QueryLedgerScreen.route
    ){
        composable(route = LedgerReportScreens.QueryLedgerScreen.route){
            QueryLedgerScreen(
                ledgerNavHostController = ledgerReportNavHostController,
                hideKeyboard = hideKeyboard,
                navHostController = navHostController,
                ledgerReportViewModel = ledgerReportViewModel
            )
        }
        composable(route = LedgerReportScreens.LedgerReportScreen.route){
            LedgerReportScreen(
                ledgerReportViewModel = ledgerReportViewModel,
                ledgerNavHostController = ledgerReportNavHostController
            )
        }
    }

}