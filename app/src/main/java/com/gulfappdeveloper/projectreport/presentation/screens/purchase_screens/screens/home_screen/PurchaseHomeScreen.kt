package com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.PurchaseViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.navigation.PurchaseScreens
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.home_screen.components.PurchaseMenuCardItem
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.navigation.SalesScreens
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_home_screen.components.MenuCardItem

@Composable
fun PurchaseHomeScreen(
    purchaseNavHostController: NavHostController,
    purchaseViewModel: PurchaseViewModel
) {
    Scaffold() {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PurchaseMenuCardItem(
                title = "Purchase Masters Report",
                subtitle = "It will give purchase masters report"
            ) {
                purchaseNavHostController.navigate(PurchaseScreens.QueryPurchaseMastersReportScreen.route)
            }
            PurchaseMenuCardItem(
                title = "Supplier Purchase Report",
                subtitle = "It will give supplier purchase report"
            ) {
                purchaseViewModel.getSupplierAccountList()
                purchaseNavHostController.navigate(PurchaseScreens.QuerySupplierPurchaseReportScreen.route)
            }
            PurchaseMenuCardItem(
                title = "Supplier Ledger Report",
                subtitle = "It will give supplier ledger report"
            ) {
                purchaseViewModel.getSupplierAccountList()
                purchaseNavHostController.navigate(PurchaseScreens.QuerySupplierLedgerReportScreen.route)
            }


        }
    }


}