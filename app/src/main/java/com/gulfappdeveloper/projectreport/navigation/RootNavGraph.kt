package com.gulfappdeveloper.projectreport.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.AccountMainScreen
import com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.LedgerReportMainScreen
import com.gulfappdeveloper.projectreport.presentation.screens.login_screen.LoginScreen
import com.gulfappdeveloper.projectreport.presentation.screens.main_screen.MainScreen
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.PurchaseMainScreen
import com.gulfappdeveloper.projectreport.presentation.screens.register_company_screen.RegisterCompanyScreen
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.SalesMainScreen
import com.gulfappdeveloper.projectreport.presentation.screens.splash_screen.SplashScreen
import com.gulfappdeveloper.projectreport.root.RootViewModel

@Composable
fun RootNavGraph(
    navHostController: NavHostController,
    hideKeyboard: () -> Unit,
    rootViewModel: RootViewModel,
    deviceId: String
) {
    NavHost(
        navController = navHostController,
        startDestination = RootNavScreens.SplashScreen.route
    ) {
        composable(RootNavScreens.SplashScreen.route) {
            SplashScreen(
                navHostController = navHostController,
                rootViewModel = rootViewModel,
                deviceId = deviceId
            )
        }
        composable(RootNavScreens.RegisterCompanyScreen.route) {
            RegisterCompanyScreen(
                navHostController = navHostController,
                hideKeyboard = hideKeyboard,
                rootViewModel = rootViewModel
            )
        }
        composable(RootNavScreens.LoginScreen.route) {
            LoginScreen(
                navHostController = navHostController,
                hideKeyboard = hideKeyboard,
                rootViewModel = rootViewModel
            )
        }
        composable(RootNavScreens.MainScreen.route) {
            MainScreen(
                navHostController = navHostController,
                rootViewModel = rootViewModel
            )
        }
        composable(
            route = RootNavScreens.LedgerReportScreens.route + "/{accountType}",
            arguments = listOf(
                navArgument(name = "accountType") {
                    type = NavType.StringType
                    defaultValue = "Customer"
                }
            )
        ) {
            LedgerReportMainScreen(
                navHostController = navHostController,
                rootViewModel = rootViewModel,
                hideKeyboard = hideKeyboard,
                accountType = it.arguments?.getString("accountType") ?: "Customer"
            )
        }
        /*composable(RootNavScreens.CustomerPaymentReportScreens.route) {
            CustomerPaymentMainScreens(
                navHostController = navHostController,
                rootViewModel = rootViewModel
            )
        }*/
        composable(RootNavScreens.SalesScreens.route) {
            SalesMainScreen(
                navHostController = navHostController,
                rootViewModel = rootViewModel,
                hideKeyboard = hideKeyboard
            )
        }
        composable(RootNavScreens.PurchaseScreens.route) {
            PurchaseMainScreen(
                navHostController = navHostController,
                rootViewModel = rootViewModel,
                hideKeyboard = hideKeyboard
            )
        }
        composable(RootNavScreens.AccountScreens.route) {
            AccountMainScreen(navHostController = navHostController)
        }

    }
}