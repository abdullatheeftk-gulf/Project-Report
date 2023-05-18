package com.gulfappdeveloper.projectreport.navigation

sealed class RootNavScreens(val route:String){
    object SplashScreen : RootNavScreens("splash_screen")
    object UniLicenseActivationScreen : RootNavScreens("uni_license_activation_screen")
    object LoginScreen : RootNavScreens("login_screen")
    object MainScreen : RootNavScreens("main_screen")
}
