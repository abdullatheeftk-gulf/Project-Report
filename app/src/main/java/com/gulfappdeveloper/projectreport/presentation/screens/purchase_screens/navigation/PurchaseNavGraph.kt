package com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.PurchaseViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.home_screen.PurchaseHomeScreen
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.purchase_masters_screen.query_screen.QueryPurchaseMastersReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.purchase_masters_screen.report_screen.PurchaseMastersReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.purchase_summary_screens.query_screen.QueryPurchaseSummaryReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.purchase_summary_screens.report_screen.PurchaseSummaryReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.supplier_ledger_screen.query_screen.QuerySupplierLedgerReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.supplier_ledger_screen.report_screen.SupplierLedgerReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.supplier_purchase_screen.query_screen.QuerySupplierPurchaseReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.supplier_purchase_screen.report_screen.SupplierPurchaseReportScreen

@Composable
fun PurchaseNavGraph(
    purchaseViewModel: PurchaseViewModel,
    navHostController: NavHostController,
    purchaseNavHostController: NavHostController,
) {
    NavHost(
        navController = purchaseNavHostController,
        startDestination = PurchaseScreens.PurchaseHomeScreen.route
    ) {
        composable(route = PurchaseScreens.PurchaseHomeScreen.route) {
            PurchaseHomeScreen(
                purchaseNavHostController = purchaseNavHostController,
                purchaseViewModel = purchaseViewModel,
                navHostController = navHostController
            )
        }
        composable(route = PurchaseScreens.QueryPurchaseMastersReportScreen.route) {
            QueryPurchaseMastersReportScreen(
                purchaseViewModel = purchaseViewModel,
                purchaseNavHostController = purchaseNavHostController
            )
        }
        composable(route = PurchaseScreens.PurchaseMastersReportScreen.route) {
            PurchaseMastersReportScreen(
                purchaseNavHostController = purchaseNavHostController,
                purchaseViewModel = purchaseViewModel
            )
        }
        composable(route = PurchaseScreens.QuerySupplierPurchaseReportScreen.route) {
            QuerySupplierPurchaseReportScreen(
                purchaseViewModel = purchaseViewModel,
                purchaseNavHostController = purchaseNavHostController
            )
        }
        composable(route = PurchaseScreens.SupplierPurchaseReportScreen.route) {
            SupplierPurchaseReportScreen(
                purchaseViewModel = purchaseViewModel,
                purchaseNavHostController = purchaseNavHostController
            )
        }
        composable(route = PurchaseScreens.QuerySupplierLedgerReportScreen.route) {
            QuerySupplierLedgerReportScreen(
                purchaseNavHostController = purchaseNavHostController,
                purchaseViewModel = purchaseViewModel
            )
        }
        composable(route = PurchaseScreens.SupplierLedgerReportScreen.route) {
            SupplierLedgerReportScreen(
                purchaseViewModel = purchaseViewModel,
                purchaseNavHostController = purchaseNavHostController
            )
        }
        composable(route = PurchaseScreens.QueryPurchaseSummaryReportScreen.route) {
            QueryPurchaseSummaryReportScreen(
                purchaseViewModel = purchaseViewModel,
                purchaseNavHostController = purchaseNavHostController
            )
        }
        composable(route = PurchaseScreens.PurchaseSummaryReportScreen.route) {
            PurchaseSummaryReportScreen(
                purchaseNavHostController = purchaseNavHostController,
                purchaseViewModel = purchaseViewModel
            )
        }
    }
}