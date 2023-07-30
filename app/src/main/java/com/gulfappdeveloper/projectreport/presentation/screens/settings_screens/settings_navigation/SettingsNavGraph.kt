package com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.settings_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.SalesViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.SettingsViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.add_company_screen.AddCompanyScreen
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.change_company_screen.ChangeCompanyScreen
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.setting_home_screen.SettingsHomeScreen
import com.gulfappdeveloper.projectreport.root.RootViewModel

@Composable
fun SettingsNavGraph(
    settingsNavHostController: NavHostController,
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    hideKeyboard: () -> Unit,
    settingsViewModel: SettingsViewModel
) {

    NavHost(
        navController = settingsNavHostController,
        startDestination = SettingsNavigationScreen.SettingsHomeScreen.route
    ) {
        composable(route = SettingsNavigationScreen.SettingsHomeScreen.route) {
            SettingsHomeScreen(
                settingNavHostController = settingsNavHostController,
                navHostController = navHostController,
                settingsViewModel = settingsViewModel
            )
        }
        composable(route = SettingsNavigationScreen.ChangeCompanyScreen.route) {
            ChangeCompanyScreen(
                settingsNavHostController = settingsNavHostController,
                settingsViewModel = settingsViewModel,
                navHostController = navHostController,
                rootViewModel = rootViewModel,
                hideKeyboard = hideKeyboard
            )
        }
        composable(route = SettingsNavigationScreen.AddCompanyScreen.route) {
            AddCompanyScreen(
                settingsNavHostController = settingsNavHostController,
                hideKeyboard = hideKeyboard,
                settingsViewModel = settingsViewModel
            )
        }

    }

}