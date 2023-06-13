package com.gulfappdeveloper.projectreport.presentation.screens.login_screen

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.R
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.root.RootViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navHostController: NavHostController,
    hideKeyboard: () -> Unit,
    rootViewModel: RootViewModel
) {
    /*var userName by remember {
        mutableStateOf("")
    }*/

    var password by remember {
        mutableStateOf("")
    }

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
                    snackBarHostState.showSnackbar(value.uiEvent.message)
                }

                is UiEvent.Navigate -> {
                    navHostController.popBackStack()
                    if (value.uiEvent.route == "Error") {
                        snackBarHostState.showSnackbar("Incorrect Password")
                    } else {
                        navHostController.navigate(value.uiEvent.route)
                    }
                }

                is UiEvent.ShowAlertDialog -> {

                }

                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Login",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                /* colors = TopAppBarDefaults.mediumTopAppBarColors(
                     //containerColor = MaterialTheme.colorScheme.primary,
                 )*/

            )
        }) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))
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
                    Text(text = "UserName")
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
                            tint = Color(0xFFFE9903)
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
