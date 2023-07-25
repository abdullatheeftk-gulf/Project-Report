package com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.account_home_screen

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
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.AccountViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.navigation.AccountScreens
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.account_home_screen.componenets.PurchaseMenuCardItem
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.PurchaseViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.navigation.SalesScreens
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_home_screen.components.MenuCardItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountHomeScreen(
    accountNavHostController: NavHostController,
    accountViewModel: AccountViewModel,
    navHostController: NavHostController
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(text = "Account Reports", textDecoration = TextDecoration.Underline)
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
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            MenuCardItem(
                title = "Expense Ledger Report",
                subtitle = "It will give Expense Ledger report"
            ) {
                accountViewModel.getCustomerAccountList()
                accountNavHostController.navigate(AccountScreens.QueryExpenseLedgerScreen.route)
            }
            MenuCardItem(
                title = "Payments Report",
                subtitle = "It will give Payments report"
            ) {
                accountNavHostController.navigate(AccountScreens.QueryPaymentsReportScreen.route)
            }
            MenuCardItem(
                title = "Receipts Report",
                subtitle = "It will give Receipts report"
            ) {
                accountNavHostController.navigate(AccountScreens.QueryReceiptsReportScreen.route)
            }




        }
    }
}