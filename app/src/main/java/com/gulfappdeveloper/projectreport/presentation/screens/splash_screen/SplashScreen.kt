package com.gulfappdeveloper.projectreport.presentation.screens.splash_screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.R
import com.gulfappdeveloper.projectreport.root.RootViewModel

private const val TAG = "SplashScreen"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    deviceId:String
) {
    val message by rootViewModel.welcomeMessage

    Log.d(TAG, "SplashScreen: $message")

    val density = LocalDensity.current

    var showProgressBar by remember {
        mutableStateOf(false)
    }


    var showLogoImage by remember {
        mutableStateOf(false)
    }

    var showLicenseExpiryAlertDialog by remember{
        mutableStateOf(false)
    }

    var showNoLicenseAlertDialog by remember{
        mutableStateOf(false)
    }



    Scaffold() {
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
                    color = Color(0xFFF57C00)
                )
            )
            Spacer(modifier = Modifier.height(50.dp))



           /* AnimatedVisibility(
                visible = showLogoImage,
                enter = slideInVertically {
                    with(density) {
                        100.dp.roundToPx()
                    }
                } + expandVertically(
                    expandFrom = Alignment.Top,

                    )
                        + fadeIn(initialAlpha = 0.3f),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
               *//* Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .width(200.dp)
                        .height(150.dp),
                    alignment = Alignment.Center
                )*//*
                
                Text(text = message)
            }
            *//*if(showPleaseWaitText){
                Text(
                    text = "Please wait while we retrieve your public IP address.",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp),
                )
            }*/
            
            Text(text = message)


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
}