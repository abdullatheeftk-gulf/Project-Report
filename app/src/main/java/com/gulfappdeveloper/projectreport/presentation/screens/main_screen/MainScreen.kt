package com.gulfappdeveloper.projectreport.presentation.screens.main_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.navigation.RootNavScreens
import com.gulfappdeveloper.projectreport.presentation.screens.main_screen.componenets.MenuItem
import com.gulfappdeveloper.projectreport.root.RootViewModel

private const val TAG = "MainScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel
) {
    Log.i(TAG, "MainScreen: ")
    val snackBarHostState = remember {
        SnackbarHostState()
    }



    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) {
        it.calculateTopPadding()
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(all = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Top,
            content = {
                /*item {
                    MenuItem(
                        onClickMenuItem = {
                           navHostController.navigate(RootNavScreens.LedgerReportScreens.route+"/Customer")
                        },
                        containerColor = Color(0xFF6ED872),
                        contentColor = Color(0xFF484545),
                        menuText = "CUSTOMER LEDGER REPORT"
                    )
                }
                item {
                    MenuItem(
                        onClickMenuItem = {
                            navHostController.navigate(RootNavScreens.LedgerReportScreens.route+"/SupplierLedgerReportScreenEvent")
                        },
                        containerColor = Color(0xFF6ED872),
                        contentColor = Color(0xFF484545),
                        menuText = "SUPPLIER LEDGER REPORT"
                    )
                }
                item {
                    MenuItem(
                        onClickMenuItem = {
                            navHostController.navigate(RootNavScreens.LedgerReportScreens.route+"/Expense")
                        },
                        containerColor = Color(0xFFF4776D),
                        contentColor = Color(0xFF484545),
                        menuText = "EXPENSE REPORT"
                    )
                }*/
                /* item {
                     MenuItem(
                         onClickMenuItem = {
                             navHostController.navigate(RootNavScreens.CustomerPaymentReportScreens.route)
                         },
                         containerColor = Color(0xFF03A9F4),
                         contentColor = Color(0xFF484545),
                         menuText = "CUSTOMER PAYMENT REPORT"
                     )
                 }*/
                item {
                    MenuItem(
                        onClickMenuItem = {
                            navHostController.navigate(RootNavScreens.SalesScreens.route)
                        },
                        containerColor = Color(0xFFFF9800),
                        contentColor = Color(0xFF484545),
                        menuText = "SALES"
                    )
                }
                item {
                    MenuItem(
                        onClickMenuItem = {
                            navHostController.navigate(RootNavScreens.PurchaseScreens.route)
                        },
                        containerColor = Color(0xFF74B8D6),
                        contentColor = Color(0xFF484545),
                        menuText = "PURCHASE"
                    )
                }
                item {
                    MenuItem(
                        onClickMenuItem = {
                             navHostController.navigate(RootNavScreens.AccountScreens.route)
                        },
                        containerColor = Color(0xFF99E597),
                        contentColor = Color(0xFF484545),
                        menuText = "ACCOUNTS"
                    )
                }
                item {
                    MenuItem(
                        onClickMenuItem = {

                        },
                        containerColor = Color(0xFFFFEB3B),
                        contentColor = Color(0xFF484545),
                        menuText = "SETTINGS"
                    )
                }

            }
        )
    }

}

/*
@Preview
@Composable
fun MenuItemPrev() {
    MainScreen()
}*/
