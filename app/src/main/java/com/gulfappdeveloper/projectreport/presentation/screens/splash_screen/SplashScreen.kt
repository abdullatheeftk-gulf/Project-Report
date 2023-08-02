package com.gulfappdeveloper.projectreport.presentation.screens.splash_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.BuildConfig
import com.gulfappdeveloper.projectreport.R
import com.gulfappdeveloper.projectreport.navigation.RootNavScreens
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.splash_screen.components.CompanyNotRegisteredAlert
import com.gulfappdeveloper.projectreport.root.AppConstants
import com.gulfappdeveloper.projectreport.root.RootViewModel
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "SplashScreen"

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
) {
    val message by rootViewModel.welcomeMessage


    val snackBarHostState = remember {
        SnackbarHostState()
    }

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var showCompanyNotRegisteredAlertDialog by remember {
        mutableStateOf(false)
    }
    var showLogoWithAnimation by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = message) {
        if (message == "Unipospro") {
            showLogoWithAnimation = true
        }
    }



    LaunchedEffect(key1 = true) {
        rootViewModel.splashScreenEvent.collectLatest { value ->
            when (value.event) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }

                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }

                is UiEvent.Navigate -> {
                    navHostController.popBackStack()
                    navHostController.navigate(value.event.route)
                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(message = value.event.message)
                }

                is UiEvent.ShowAlertDialog -> {

                    if (value.event.message == AppConstants.COMPANY_NOT_REGISTERED) {
                        showCompanyNotRegisteredAlertDialog = true
                    }
                }

                else -> Unit
            }
        }
    }


    if (showCompanyNotRegisteredAlertDialog) {
        CompanyNotRegisteredAlert {
            showCompanyNotRegisteredAlertDialog = false
            navHostController.popBackStack()
            navHostController.navigate(RootNavScreens.RegisterCompanyScreen.route)
        }
    }



    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) {
        it.calculateTopPadding()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Image(
                painter = painterResource(id = R.drawable.laptop_24),
                contentDescription = null,
                modifier = Modifier.size(250.dp),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(50.dp))


            if (message != "Unipospro") {
                Text(
                    text = message,
                    color = if (message == "No internet connectivity") Color.Red else MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = MaterialTheme.typography.headlineMedium.fontWeight,
                    fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            AnimatedVisibility(
                visible = showLogoWithAnimation && message == "Unipospro",
                enter = slideInVertically(
                    animationSpec = tween(
                        durationMillis = 2000,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    800
                }
                        + fadeIn(
                    initialAlpha = 0.1f,
                    animationSpec = tween(
                        durationMillis = 2000, easing = LinearOutSlowInEasing
                    )
                )
            ) {
                Image(
                    painter = painterResource(
                        id = if (BuildConfig.APP_STATUS)
                            R.drawable.mult_logo
                        else
                            R.drawable.single_logo
                    ), contentDescription = null, modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(16.dp)
                )
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