package com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.change_company_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.domain.models.room.LocalCompanyData
import com.gulfappdeveloper.projectreport.navigation.RootNavScreens
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.SettingsViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.change_company_screen.componenets.AlertDialogForLocalCompanyDataSelection
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.change_company_screen.componenets.CompanyItems
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.settings_navigation.SettingsNavigationScreen
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "ChangeCompanyScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeCompanyScreen(
    settingsNavHostController: NavHostController,
    navHostController: NavHostController,
    settingsViewModel: SettingsViewModel
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val localCompanyDataList = settingsViewModel.localCompanyDataList
    val selectedCompanyId by settingsViewModel.selectedCompanyId
    Log.d(TAG, "ChangeCompanyScreen: $selectedCompanyId")

    var alertDialogLocalCompanyData: LocalCompanyData? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = true){
        settingsViewModel.changeCompanyScreenEvent.collectLatest {value ->
            when(value.uiEvent){
                is UiEvent.ShowSnackBar->{
                    snackBarHostState.showSnackbar(value.uiEvent.message, duration = SnackbarDuration.Long)
                }
                else->Unit
            }
        }
    }

    alertDialogLocalCompanyData?.let {
        AlertDialogForLocalCompanyDataSelection(
            localCompanyData = it,
            onDismissRequest = {
                alertDialogLocalCompanyData = null
                navHostController.popBackStack(route = RootNavScreens.MainScreen.route,inclusive = true)
                navHostController.navigate(route = RootNavScreens.LoginScreen.route)

            },
            settingsViewModel = settingsViewModel
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Change Company", textDecoration = TextDecoration.Underline)
                },
                navigationIcon = {
                    IconButton(onClick = { settingsNavHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                settingsNavHostController.navigate(
                    SettingsNavigationScreen.AddCompanyScreen.route
                )
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        paddingValues.calculateTopPadding()
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(localCompanyDataList) { localCompanyData ->

                CompanyItems(
                    localCompanyData = localCompanyData,
                    selectedCompanyId = selectedCompanyId,
                    onCheckBoxClicked = {
                        alertDialogLocalCompanyData = it
                    }
                )
            }
        }

    }
}

