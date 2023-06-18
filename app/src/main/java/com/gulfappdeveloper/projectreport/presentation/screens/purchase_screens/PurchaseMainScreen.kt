package com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.navigation.PurchaseNavGraph
import com.gulfappdeveloper.projectreport.root.RootViewModel


@Composable
fun PurchaseMainScreen(
    rootViewModel: RootViewModel,
    navHostController: NavHostController,
    hideKeyboard: () -> Unit,
    purchaseViewModel: PurchaseViewModel = hiltViewModel()
) {
    val purchaseNavHostControl = rememberNavController()
    PurchaseNavGraph(
        purchaseViewModel = purchaseViewModel,
        purchaseNavHostController = purchaseNavHostControl
    )
}