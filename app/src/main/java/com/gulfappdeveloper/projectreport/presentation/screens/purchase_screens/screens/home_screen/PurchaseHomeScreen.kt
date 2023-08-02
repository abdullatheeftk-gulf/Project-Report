package com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.home_screen

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
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.PurchaseViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.navigation.PurchaseScreens
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.home_screen.components.PurchaseMenuCardItem
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.navigation.SalesScreens
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_home_screen.components.MenuCardItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseHomeScreen(
    purchaseNavHostController: NavHostController,
    navHostController: NavHostController,
    purchaseViewModel: PurchaseViewModel
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Purchase Reports",
                    textDecoration = TextDecoration.Underline,
                )
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
    }) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp, top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PurchaseMenuCardItem(
                title = "Purchase Masters Report",
            ) {
                purchaseNavHostController.navigate(PurchaseScreens.QueryPurchaseMastersReportScreen.route)
            }
            PurchaseMenuCardItem(
                title = "Purchase Summary Report",
            ) {
                purchaseNavHostController.navigate(PurchaseScreens.QueryPurchaseSummaryReportScreen.route)
            }
            PurchaseMenuCardItem(
                title = "Supplier Purchase Report",
            ) {
                purchaseViewModel.getSupplierAccountList()
                purchaseNavHostController.navigate(PurchaseScreens.QuerySupplierPurchaseReportScreen.route)
            }
            PurchaseMenuCardItem(
                title = "Supplier Ledger Report",
            ) {
                purchaseViewModel.getSupplierAccountList()
                purchaseNavHostController.navigate(PurchaseScreens.QuerySupplierLedgerReportScreen.route)
            }


        }
    }


}