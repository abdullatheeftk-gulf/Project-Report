/*package com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens.navigation.CustomerPaymentGraph
import com.gulfappdeveloper.projectreport.root.RootViewModel


@Composable
fun CustomerPaymentMainScreens(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    customerPaymentScreenViewModel: CustomerPaymentScreenViewModel = hiltViewModel()
) {

   val customerPaymentNavHostController = rememberNavController()

   CustomerPaymentGraph(
       customerPaymentNavHostController = customerPaymentNavHostController,
       customerPaymentRootViewModel = customerPaymentScreenViewModel,
       navHostController = navHostController
   )

}*/
