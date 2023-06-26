package com.gulfappdeveloper.projectreport.presentation.screens.account_screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.AccountViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.account_home_screen.AccountHomeScreen
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.expense_ledger_report_screen.query.QueryExpenseLEdgerScreen
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.expense_ledger_report_screen.report.ExpenseLedgerReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.payments_report_screen.query.QueryPaymentsReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.payments_report_screen.report.PaymentsReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.receipts_report_screen.query.QueryReceiptsReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.receipts_report_screen.report.ReceiptsReportScreen

@Composable
fun AccountNavGraph(
    accountNavHostController: NavHostController,
    accountViewModel: AccountViewModel
) {
    NavHost(
        navController = accountNavHostController,
        startDestination = AccountScreens.HomeScreen.route
    ) {
        composable(route = AccountScreens.HomeScreen.route) {
            AccountHomeScreen(accountNavHostController = accountNavHostController, accountViewModel)
        }
        composable(route = AccountScreens.QueryExpenseLedgerScreen.route) {
            QueryExpenseLEdgerScreen(
                accountNavHostController = accountNavHostController,
                accountViewModel = accountViewModel
            )
        }
        composable(route = AccountScreens.ExpenseLedgerScreen.route) {
            ExpenseLedgerReportScreen(
                accountNavHostController = accountNavHostController,
                accountViewModel = accountViewModel
            )
        }
        composable(route = AccountScreens.QueryPaymentsReportScreen.route) {
            QueryPaymentsReportScreen(
                accountNavHostController = accountNavHostController,
                accountViewModel = accountViewModel
            )
        }
        composable(route = AccountScreens.PaymentsReportScreen.route) {
            PaymentsReportScreen(
                accountNavHostController = accountNavHostController,
                accountViewModel = accountViewModel
            )
        }
        composable(route = AccountScreens.QueryReceiptsReportScreen.route) {
            QueryReceiptsReportScreen(
                accountNavHostController = accountNavHostController,
                accountViewModel = accountViewModel
            )
        }
        composable(route = AccountScreens.ReceiptsReportScreen.route) {
            ReceiptsReportScreen(
                accountNavHostController = accountNavHostController,
                accountViewModel = accountViewModel
            )
        }

    }
}