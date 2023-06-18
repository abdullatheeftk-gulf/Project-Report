package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.SalesViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.navigation.SalesScreens
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_home_screen.components.MenuCardItem

@Composable
fun SaleHomeScreen(
    salesNavHostController: NavHostController,
    salesViewModel: SalesViewModel
) {

    Scaffold() {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MenuCardItem(title = "Customer Payment Report", subtitle = "It will give Customer Payment report") {
                salesNavHostController.navigate(SalesScreens.QueryCustomerPaymentReportScreen.route)
            }
            MenuCardItem(title = "Customer Ledger Report", subtitle = "It will give User Sales report") {
                salesViewModel.getCustomerAccountList()
                salesNavHostController.navigate(SalesScreens.QueryCustomerLedgerReportScreen.route)
            }
            MenuCardItem(title = "Sales Invoice Report", subtitle = "It will give sales Invoice report") {
                salesNavHostController.navigate(SalesScreens.QuerySalesInvoiceReportScreen.route)
            }
            MenuCardItem(title = "Sale Summaries Report", subtitle = "It will give sales Summaries report") {
                salesNavHostController.navigate(SalesScreens.QuerySaleSummariesReportScreen.route)
            }
            MenuCardItem(title = "Pos Payment Report", subtitle = "It will give Pos Payment report") {
                salesNavHostController.navigate(SalesScreens.QueryPosPaymentReportScreen.route)
            }
            MenuCardItem(title = "User Sales Report", subtitle = "It will give User Sales report") {
                salesNavHostController.navigate(SalesScreens.QueryUserSalesReport.route)
            }


        }
    }
}