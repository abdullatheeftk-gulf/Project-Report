/*
package com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens.screens.customer_payment_report_screen

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.R
import com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens.CustomerPaymentScreenViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens.screens.customer_payment_report_screen.components.CustomerPaymentReportTable

private const val TAG = "CustomerPaymentReportSc"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerPaymentReportScreen(
    customerPaymentNavHostController: NavHostController,
    customerPaymentScreenViewModel: CustomerPaymentScreenViewModel
) {

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val fromDate by customerPaymentScreenViewModel.fromDateState
    val toDate by customerPaymentScreenViewModel.toDateState


    val customerPaymentReportList = customerPaymentScreenViewModel.customerPaymentReportList


    */
/*LaunchedEffect(key1 = true) {

    }*//*



    val context = LocalContext.current





    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Customer Payment Report",
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        customerPaymentNavHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    ScreenOrientationAction(
                        context = context,
                        customerPaymentScreenViewModel = customerPaymentScreenViewModel
                    )
                }
            )
        }
    ) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            Row(modifier = Modifier.fillMaxWidth()) {

                Text(text = "Report from ")
                Text(text = fromDate, color = MaterialTheme.colorScheme.primary, fontSize = 17.sp)
                Text(text = " to ")
                Text(text = toDate, color = MaterialTheme.colorScheme.primary, fontSize = 17.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))

            if (customerPaymentReportList.isEmpty()){
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Empty List",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.alpha(.5f),
                        fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                        fontWeight = MaterialTheme.typography.headlineMedium.fontWeight
                    )
                }
                try {
                    return@Scaffold
                }catch (e:Exception){
                    Log.e(TAG, "CustomerPaymentReportScreen: $e", )
                }

            }

            CustomerPaymentReportTable(customerPaymentReportList = customerPaymentReportList)
        }

    }

}

@Composable
fun ScreenOrientationAction(
    context: Context,
    customerPaymentScreenViewModel: CustomerPaymentScreenViewModel
) {
    val activity = context as Activity
    val portrait by customerPaymentScreenViewModel.orientation
    IconButton(onClick = {
        Log.d(TAG, "CustomerPaymentReportScreen: ${portrait}")
        if (portrait) {

            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {

            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
        customerPaymentScreenViewModel.setOrientation(!portrait)

    }) {
        Icon(
            painter = painterResource(
                id = R.drawable.baseline_screen_rotation_24
            ),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}*/
