package com.gulfappdeveloper.projectreport.presentation.screens.account_screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.navigation.AccountNavGraph

@Composable
fun AccountMainScreen(
    navHostController: NavHostController,
    accountViewModel: AccountViewModel = hiltViewModel()
) {
    val accountNavHostController = rememberNavController()

    AccountNavGraph(accountNavHostController = accountNavHostController, accountViewModel = accountViewModel)

}