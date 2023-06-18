package com.gulfappdeveloper.projectreport.navigation

sealed class RootNavScreens(val route: String) {
    object SplashScreen : RootNavScreens("splash_screen")
    object UniLicenseActivationScreen : RootNavScreens("uni_license_activation_screen")
    object RegisterCompanyScreen : RootNavScreens("company_registration_screen")
    object LoginScreen : RootNavScreens("login_screen")
    object MainScreen : RootNavScreens("main_screen")
    object LedgerReportScreens : RootNavScreens("Ledger_report_screens")
    object SalesScreens : RootNavScreens("sales_screens")
    object PurchaseScreens : RootNavScreens("purchase_screens")
}
