package com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.settings_navigation

sealed class SettingsNavigationScreen(val route: String) {
    object SettingsHomeScreen : SettingsNavigationScreen("settings_home_screen")
    object ChangeCompanyScreen : SettingsNavigationScreen("change_company_screen")
    object AddCompanyScreen : SettingsNavigationScreen("add_company_screen")
}