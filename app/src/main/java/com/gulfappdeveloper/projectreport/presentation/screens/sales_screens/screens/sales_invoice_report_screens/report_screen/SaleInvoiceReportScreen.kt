package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_invoice_report_screens.report_screen

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.R
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.SalesViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_invoice_report_screens.report_screen.components.SaleInvoiceReportTable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleInvoiceReportScreen(
    saleNavHostController: NavHostController,
    salesViewModel: SalesViewModel
) {
    val fromDate by salesViewModel.fromDateState
    val toDate by salesViewModel.toDateState

    val saleInvoiceReportList = salesViewModel.salesInvoiceReportList

    val context = LocalContext.current

    val orientation by remember {
        mutableStateOf(context.resources.configuration.orientation)
    }
    salesViewModel.setOrientation(orientation)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sale Invoice Report",
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        saleNavHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    ScreenOrientationActionForSaleInvoice(
                        context = context,
                        salesViewModel = salesViewModel
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

            if (saleInvoiceReportList.isEmpty()){
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
                }catch (_:Exception){

                }

            }
            SaleInvoiceReportTable(saleInvoiceReportList = saleInvoiceReportList)
        }
    }
}

@Composable
fun ScreenOrientationActionForSaleInvoice(
    context: Context,
    salesViewModel: SalesViewModel
) {
    val activity = context as Activity
    val portrait by salesViewModel.orientation
    IconButton(onClick = {

        if (portrait) {

            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {

            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
        // salesViewModel.setOrientation(!portrait)

    }) {
        Icon(
            painter = painterResource(
                id = R.drawable.baseline_screen_rotation_24
            ),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
