package com.gulfappdeveloper.projectreport.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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

    }
}