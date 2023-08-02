package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.SalesViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.navigation.SalesScreens
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_home_screen.components.MenuCardItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleHomeScreen(
    salesNavHostController: NavHostController,
    navHostController: NavHostController,
    salesViewModel: SalesViewModel
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Sales Reports", textDecoration = TextDecoration.Underline)
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp,end=8.dp,top=it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            MenuCardItem(
                title = "Customer Payment Report",
            ) {
                salesNavHostController.navigate(SalesScreens.QueryCustomerPaymentReportScreen.route)
            }
            MenuCardItem(
                title = "Customer Ledger Report",
            ) {
                salesViewModel.getCustomerAccountList()
                salesNavHostController.navigate(SalesScreens.QueryCustomerLedgerReportScreen.route)
            }
            MenuCardItem(
                title = "Sales Invoice Report",
            ) {
                salesNavHostController.navigate(SalesScreens.QuerySalesInvoiceReportScreen.route)
            }
            MenuCardItem(
                title = "Sale Summaries Report",
            ) {
                salesNavHostController.navigate(SalesScreens.QuerySaleSummariesReportScreen.route)
            }
            MenuCardItem(
                title = "Pos Payment Report",
            ) {
                salesNavHostController.navigate(SalesScreens.QueryPosPaymentReportScreen.route)
            }
            MenuCardItem(
                title = "User Sales Report",
            ) {
                salesNavHostController.navigate(SalesScreens.QueryUserSalesReport.route)
            }


        }
    }
}