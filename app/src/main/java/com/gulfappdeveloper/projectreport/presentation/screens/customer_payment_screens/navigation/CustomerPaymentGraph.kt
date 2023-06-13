/*
package com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens.CustomerPaymentScreenViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens.screens.customer_payment_report_screen.CustomerPaymentReportScreen
import com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens.screens.query_customer_payment_screen.QueryCustomerPaymentScreen

@Composable
fun CustomerPaymentGraph(
    customerPaymentNavHostController: NavHostController,
    navHostController: NavHostController,
    customerPaymentRootViewModel: CustomerPaymentScreenViewModel
) {
    NavHost(
        navController = customerPaymentNavHostController,
        startDestination = CustomerPaymentScreens.QueryCustomerPaymentScreen.route
    ){
        composable(route = CustomerPaymentScreens.QueryCustomerPaymentScreen.route){
            QueryCustomerPaymentScreen(
                navHostController = navHostController,
                customerPaymentNavHostController = customerPaymentNavHostController,
                customerPaymentScreenViewModel = customerPaymentRootViewModel
            )
        }
        composable(route = CustomerPaymentScreens.CustomerPaymentReportScreen.route){
            CustomerPaymentReportScreen(
                customerPaymentNavHostController = customerPaymentNavHostController,
                customerPaymentScreenViewModel = customerPaymentRootViewModel
            )
        }

    }
}*/
