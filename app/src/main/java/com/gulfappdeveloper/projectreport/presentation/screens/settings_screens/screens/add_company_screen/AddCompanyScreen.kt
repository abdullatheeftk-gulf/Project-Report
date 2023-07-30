package com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.add_company_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCompanyScreen(
    settingsNavHostController: NavHostController,
    hideKeyboard:()->Unit,
    settingsViewModel: SettingsViewModel
) {
    var companyCode by remember {
        mutableStateOf("")
    }


    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var showErrorOnRegistration by remember {
        mutableStateOf(false)
    }
    var errorText by remember {
        mutableStateOf("")
    }


    LaunchedEffect(key1 = true) {
       settingsViewModel.addCompanyScreenEvent.collectLatest { value ->
            when (value.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }

                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }

                is UiEvent.Navigate -> {
                    /*navHostController.popBackStack()
                    navHostController.navigate(value.uiEvent.route)*/
                    settingsNavHostController.popBackStack()
                }

                is UiEvent.ShowAlertDialog -> {

                }

                is UiEvent.ShowSnackBar -> {
                    errorText = value.uiEvent.message
                    showErrorOnRegistration = true
                }

                else -> Unit
            }
        }

    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Register Your company",
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { settingsNavHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack,contentDescription = null)
                    }
                }


            )
        }
    ) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = it.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            OutlinedTextField(
                value = companyCode,
                onValueChange = { value ->
                    companyCode = value
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Company code")
                },
                placeholder = {
                    Text(
                        text = "Enter Company Code",
                        modifier = Modifier.alpha(0.5f)
                    )
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard()
                        settingsViewModel.registerCompany(companyCode = companyCode)
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                supportingText = {
                    if (showErrorOnRegistration) {
                        Text(
                            text = errorText,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    if (companyCode.isNotEmpty() || companyCode.isNotBlank()) {
                        hideKeyboard()
                        settingsViewModel.registerCompany(companyCode = companyCode)
                        showErrorOnRegistration = false
                    }else{
                        showErrorOnRegistration = true
                        errorText = "Empty Company code"
                    }

                },
                enabled = !showProgressBar
            ) {
                Text(text = "Register")
            }
        }


    }

    if (showProgressBar) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}