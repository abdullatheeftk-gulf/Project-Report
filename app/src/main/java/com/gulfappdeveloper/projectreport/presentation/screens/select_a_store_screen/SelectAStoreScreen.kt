package com.gulfappdeveloper.projectreport.presentation.screens.select_a_store_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.navigation.RootNavScreens
import com.gulfappdeveloper.projectreport.presentation.screens.select_a_store_screen.componenets.StoreItems
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.SettingsViewModel
import com.gulfappdeveloper.projectreport.root.RootViewModel
import com.gulfappdeveloper.projectreport.ui.theme.MySecondaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAStoreScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
) {

    val snackBarHostState = remember {
        SnackbarHostState()
    }



    val localCompanyDataList = rootViewModel.localCompanyDataList
    val selectedCompanyId by rootViewModel.selectedCompanyId

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Select A Store", textDecoration = TextDecoration.Underline)
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            Button(onClick = {
                navHostController.navigate(
                    RootNavScreens.LoginScreen.route
                )
            }) {
                Text(text = "Login")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null, tint = MySecondaryColor)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { 
                Spacer(modifier = Modifier.height(20.dp))
            }

            items(localCompanyDataList) { localCompanyData ->

                StoreItems(
                    localCompanyData = localCompanyData,
                    selectedCompanyId = selectedCompanyId,
                    onCheckBoxClicked =rootViewModel::savePreferredStoreToDataStore
                )
            }
        }

    }

}