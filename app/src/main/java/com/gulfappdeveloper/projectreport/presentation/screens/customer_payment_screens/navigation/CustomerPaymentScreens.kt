package com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens.navigation

sealed class CustomerPaymentScreens(val route:String){
    object QueryCustomerPaymentScreen:CustomerPaymentScreens("query_customer_payment")
    object CustomerPaymentReportScreen:CustomerPaymentScreens("customer_payment_report")
}
