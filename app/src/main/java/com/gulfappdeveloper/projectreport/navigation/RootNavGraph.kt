package com.gulfappdeveloper.projectreport.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.AccountMainScreen
import com.gulfappdeveloper.projectreport.presentation.screens.login_screen.LoginScreen
import com.gulfappdeveloper.projectreport.presentation.screens.main_screen.MainScreen
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.PurchaseMainScreen
import com.gulfappdeveloper.projectreport.presentation.screens.register_company_screen.RegisterCompanyScreen
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.SalesMainScreen
import com.gulfappdeveloper.projectreport.presentation.screens.select_a_store_screen.SelectAStoreScreen
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.SettingsMainScreen
import com.gulfappdeveloper.projectreport.presentation.screens.splash_screen.SplashScreen
import com.gulfappdeveloper.projectreport.root.RootViewModel

@Composable
fun RootNavGraph(
    navHostController: NavHostController,
    hideKeyboard: () -> Unit,
    rootViewModel: RootViewModel,
    deviceId: String
) {
    rootViewModel.saveDeviceIdUseCase(deviceId = deviceId)
    NavHost(
        navController = navHostController,
        startDestination = RootNavScreens.SplashScreen.route
    ) {
        composable(RootNavScreens.SplashScreen.route) {
            SplashScreen(
                navHostController = navHostController,
                rootViewModel = rootViewModel,
            )
        }
        composable(RootNavScreens.RegisterCompanyScreen.route) {
            RegisterCompanyScreen(
                navHostController = navHostController,
                hideKeyboard = hideKeyboard,
                rootViewModel = rootViewModel
            )
        }
        composable(RootNavScreens.SelectAStoreScreen.route) {
            SelectAStoreScreen(navHostController = navHostController, rootViewModel = rootViewModel)
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
        composable(route = RootNavScreens.SettingsScreen.route) {
            SettingsMainScreen(
                navHostController = navHostController,
                rootViewModel = rootViewModel,
                hideKeyboard = hideKeyboard
            )
        }

    }
}