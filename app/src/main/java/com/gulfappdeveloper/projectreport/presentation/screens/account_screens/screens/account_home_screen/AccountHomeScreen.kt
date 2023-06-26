package com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.account_home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.AccountViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.navigation.AccountScreens
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.account_home_screen.componenets.PurchaseMenuCardItem
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.PurchaseViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.navigation.SalesScreens
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_home_screen.components.MenuCardItem

@Composable
fun AccountHomeScreen(
    accountNavHostController: NavHostController,
    accountViewModel: AccountViewModel
) {
    Scaffold() {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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