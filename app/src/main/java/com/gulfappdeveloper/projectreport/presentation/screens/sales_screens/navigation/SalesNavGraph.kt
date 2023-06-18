package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.SalesViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.customer_ledger_screens.query_screen.QueryCustomerLedgerReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.customer_ledger_screens.report_screen.CustomerLedgerReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.customer_payment_report_screens.query_screen.QueryCustomerPaymentReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.customer_payment_report_screens.report_screen.CustomerPaymentReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.pos_payment_report_screen.query_screen.QueryPosPaymentReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.pos_payment_report_screen.report_screen.PosPaymentReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sale_summaries_report_screens.query_screen.QuerySaleSummariesReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sale_summaries_report_screens.report_screen.SaleSummaryReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_home_screen.SaleHomeScreen
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_invoice_report_screens.query_screen.QuerySalesInvoiceReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_invoice_report_screens.report_screen.SaleInvoiceReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.user_sales_report_screens.query_screen.QueryUserSalesReport
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.user_sales_report_screens.report_screen.UserSalesReportScreen
import com.gulfappdeveloper.projectreport.root.RootViewModel

@Composable
fun SalesNavGraph(
    salesNavHostController: NavHostController,
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    hideKeyboard: () -> Unit,
    salesViewModel: SalesViewModel
) {
    NavHost(
        navController = salesNavHostController,
        startDestination = SalesScreens.SalesHomeScreen.route
    ) {

        composable(route = SalesScreens.SalesHomeScreen.route) {
            SaleHomeScreen(
                salesNavHostController = salesNavHostController,
                salesViewModel = salesViewModel
            )
        }
        composable(route = SalesScreens.QuerySalesInvoiceReportScreen.route) {
            QuerySalesInvoiceReportScreen(
                salesNavHostController = salesNavHostController,
                salesViewModel = salesViewModel
            )
        }
        composable(route = SalesScreens.SalesInvoiceReportScreen.route) {
            SaleInvoiceReportScreen(
                saleNavHostController = salesNavHostController,
                salesViewModel = salesViewModel
            )
        }
        composable(route = SalesScreens.QuerySaleSummariesReportScreen.route) {
            QuerySaleSummariesReportScreen(
                salesNavHostController = salesNavHostController,
                salesViewModel = salesViewModel
            )
        }
        composable(route = SalesScreens.SaleSummariesReportScreen.route) {
            SaleSummaryReportScreen(
                saleNavHostController = salesNavHostController,
                salesViewModel = salesViewModel
            )
        }
        composable(route = SalesScreens.QueryUserSalesReport.route) {
            QueryUserSalesReport(
                salesNavHostController = salesNavHostController,
                salesViewModel = salesViewModel
            )
        }
        composable(route = SalesScreens.UserSalesReportScreen.route) {
            UserSalesReportScreen(
                salesNavHostController = salesNavHostController,
                salesViewModel = salesViewModel
            )
        }

        composable(route = SalesScreens.QueryCustomerPaymentReportScreen.route) {
            QueryCustomerPaymentReportScreen(
                salesNavHostController = salesNavHostController,
                salesViewModel = salesViewModel
            )
        }
        composable(route = SalesScreens.CustomerPaymentReportScreen.route) {
            CustomerPaymentReportScreen(
                salesViewModel = salesViewModel,
                salesNavHostController = salesNavHostController
            )
        }
        composable(route = SalesScreens.QueryCustomerLedgerReportScreen.route) {
            QueryCustomerLedgerReportScreen(
                saleNavHostController = salesNavHostController,
                salesViewModel = salesViewModel
            )
        }

        composable(route = SalesScreens.CustomerLedgerReportScreen.route) {
            CustomerLedgerReportScreen(
                saleNavHostController = salesNavHostController,
                salesViewModel = salesViewModel
            )
        }
        composable(route = SalesScreens.QueryPosPaymentReportScreen.route) {
                QueryPosPaymentReportScreen(
                    salesViewModel = salesViewModel,
                    salesNavHostController = salesNavHostController
                )
        }
        composable(route = SalesScreens.PosPaymentReportScreen.route) {
                PosPaymentReportScreen(saleNavHostController = salesNavHostController, salesViewModel = salesViewModel)
        }

    }

}