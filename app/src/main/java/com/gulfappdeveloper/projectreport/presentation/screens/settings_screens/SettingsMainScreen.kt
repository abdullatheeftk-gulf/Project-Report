package com.gulfappdeveloper.projectreport.presentation.screens.settings_screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.settings_navigation.SettingsNavGraph
import com.gulfappdeveloper.projectreport.root.RootViewModel

@Composable
fun SettingsMainScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    hideKeyboard: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    val settingNavHostController = rememberNavController()
    SettingsNavGraph(
        settingsNavHostController = settingNavHostController,
        navHostController = navHostController,
        rootViewModel =rootViewModel ,
        hideKeyboard = hideKeyboard,
        settingsViewModel = settingsViewModel
    )

}