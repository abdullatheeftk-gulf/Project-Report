package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens

import androidx.compose.material3.RichTooltipBox
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.navigation.SalesNavGraph
import com.gulfappdeveloper.projectreport.root.RootViewModel
import eu.wewox.minabox.MinaBox

@Composable
fun SalesMainScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    hideKeyboard:()->Unit,
    salesViewModel: SalesViewModel = hiltViewModel()
) {
    val salesScreensNavHostController = rememberNavController()

    SalesNavGraph(
        salesNavHostController = salesScreensNavHostController,
        navHostController = navHostController,
        rootViewModel = rootViewModel,
        hideKeyboard = hideKeyboard,
        salesViewModel = salesViewModel
    )

}

