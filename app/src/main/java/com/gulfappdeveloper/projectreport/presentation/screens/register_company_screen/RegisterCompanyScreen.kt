package com.gulfappdeveloper.projectreport.presentation.screens.register_company_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.root.RootViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterCompanyScreen(
    navHostController: NavHostController,
    hideKeyboard: () -> Unit,
    rootViewModel: RootViewModel
) {
    var companyCode by remember {
        mutableStateOf("")
    }
    /*var companyName by remember {
        mutableStateOf("")
    }*/

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
        rootViewModel.registerCompanyScreenEvent.collectLatest { value ->
            when (value.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }

                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }

                is UiEvent.Navigate -> {
                    navHostController.popBackStack()
                    navHostController.navigate(value.uiEvent.route)
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
            TopAppBar(
                title = {
                    Text(
                        text = "Register Your company",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    //containerColor = MaterialTheme.colorScheme.primary,
                )

            )
        }
    ) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(70.dp))
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
                        rootViewModel.registerCompany(companyCode = companyCode)
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
            /*Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Or")
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = companyName,
                onValueChange = { value ->
                    companyName = value
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Company Name")
                },
                placeholder = {
                    Text(text = "Enter Company Name")
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        rootViewModel.registerCompany(taxIdOrCompanyName = companyName)
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )*/
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    if (companyCode.isNotEmpty() || companyCode.isNotBlank()) {
                        hideKeyboard()
                        rootViewModel.registerCompany(companyCode = companyCode)
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

