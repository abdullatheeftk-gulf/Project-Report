package com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.setting_home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.navigation.RootNavScreens
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_home_screen.components.MenuCardItem
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.SettingsViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.settings_navigation.SettingsNavigationScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsHomeScreen(
    settingNavHostController: NavHostController,
    navHostController: NavHostController,
    settingsViewModel: SettingsViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Settings", textDecoration = TextDecoration.Underline)
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },

    ) { paddingValues ->
        Column(modifier = Modifier.padding(vertical = paddingValues.calculateTopPadding(), horizontal =8.dp )) {
            MenuCardItem(
                title = "Change Store",
                subtitle = "you can change Store for reports"
            ) {
                settingNavHostController.navigate(SettingsNavigationScreen.ChangeCompanyScreen.route)
            }
            MenuCardItem(
                title = "Logout",
                subtitle = "Logout from this session"
            ) {
                navHostController.navigate(route = RootNavScreens.LoginScreen.route){
                    popUpTo(route = RootNavScreens.MainScreen.route){
                        inclusive = true
                    }
                }
            }
        }

    }
}