package com.gulfappdeveloper.projectreport.presentation.screens.login_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.R
import com.gulfappdeveloper.projectreport.navigation.RootNavScreens
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.login_screen.components.LoginErrorMessageAlertDialog
import com.gulfappdeveloper.projectreport.root.RootViewModel
import com.gulfappdeveloper.projectreport.ui.theme.MySecondaryColor
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navHostController: NavHostController,
    hideKeyboard: () -> Unit,
    rootViewModel: RootViewModel
) {

    val isNavFromSettingScreenToLoginScreen by rootViewModel.isNavFromSettingToLogin

    var password by remember {
        mutableStateOf("")
    }

    val localCompanyDataList = rootViewModel.localCompanyDataList

    val selectedStore by rootViewModel.selectedStore

    var showPasswordToggle by remember {
        mutableStateOf(false)
    }

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    var showErrorMessage by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    var alertDialogMessage: String? by remember {
        mutableStateOf(null)
    }

    val userNameState by rootViewModel.userNameState

    LaunchedEffect(key1 = true) {
        rootViewModel.loginScreenEvent.collectLatest { value ->
            when (value.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }

                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }

                is UiEvent.ShowSnackBar -> {
                    showErrorMessage = true
                    errorMessage = value.uiEvent.message
                    snackBarHostState.showSnackbar(
                        value.uiEvent.message,
                        duration = SnackbarDuration.Long
                    )
                }

                is UiEvent.Navigate -> {
                    if (localCompanyDataList.size > 1) {
                        if (isNavFromSettingScreenToLoginScreen) {
                            navHostController.navigate(value.uiEvent.route) {
                                popUpTo(route = RootNavScreens.MainScreen.route) {
                                    inclusive = true
                                }
                            }
                        } else {
                            navHostController.navigate(value.uiEvent.route) {
                                popUpTo(route = RootNavScreens.SelectAStoreScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    } else {
                        navHostController.popBackStack()
                        navHostController.navigate(value.uiEvent.route)
                    }

                }

                is UiEvent.ShowAlertDialog -> {
                    alertDialogMessage = value.uiEvent.message
                }

                else -> Unit
            }
        }
    }

    alertDialogMessage?.let {
        LoginErrorMessageAlertDialog(message = it) {
            alertDialogMessage = null
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Login",
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                },
                modifier = Modifier.fillMaxWidth(),

                navigationIcon = {
                    if (localCompanyDataList.size > 1) {
                        IconButton(onClick = {
                            navHostController.popBackStack()
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

            )
        }) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = it.calculateTopPadding(), start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(text = "Selected Store : ", modifier = Modifier)
                Text(
                    text = selectedStore?.name.toString(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(
                value = userNameState,
                onValueChange = { value ->
                    rootViewModel.setUserName(value)
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {

                    }
                ),
                placeholder = {
                    Text(
                        text = "Enter User Name",
                        modifier = Modifier.alpha(0.5f)
                    )
                },
                label = {
                    Text(text = "User Name")
                },
                supportingText = {
                    if (showErrorMessage) {
                        Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { value ->
                    password = value
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        showErrorMessage = false
                        rootViewModel.login(userName = userNameState, password = password)
                        hideKeyboard()
                    }
                ),
                placeholder = {
                    Text(
                        text = "Enter Password",
                        modifier = Modifier.alpha(0.5f)
                    )
                },
                label = {
                    Text(text = "Password")
                },
                visualTransformation = if (showPasswordToggle) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        showPasswordToggle = !showPasswordToggle
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (showPasswordToggle) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24
                            ),
                            contentDescription = null,
                            tint = MySecondaryColor
                        )
                    }

                }

            )


            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    showErrorMessage = false
                    rootViewModel.login(userName = userNameState, password = password)
                    hideKeyboard()
                },
                enabled = !showProgressBar
            ) {
                Text(text = "Login")
            }

        }
    }

    if (showProgressBar) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

/*
@Preview
@Composable
fun LoginScreenPrev() {
    LoginScreen()
}*/
