package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.customer_ledger_screens.query_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.R
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.SalesViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueryCustomerLedgerReportScreen(
    saleNavHostController: NavHostController,
    salesViewModel: SalesViewModel
) {

    var selectedFromDate by remember {
        mutableStateOf<LocalDate>(LocalDate.now().minusYears(1))
    }
    var selectedToDate by remember {
        mutableStateOf<LocalDate>(LocalDate.now())
    }

    val fromDateState = rememberUseCaseState()
    val toDateState = rememberUseCaseState()

    CalendarDialog(
        state = fromDateState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date(
            selectedDate = selectedFromDate
        ) { newDate ->
            selectedFromDate = newDate
            fromDateState.hide()
        }
    )

    CalendarDialog(
        state = toDateState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date(
            selectedDate = selectedToDate
        ) { newDate ->
            selectedToDate = newDate
            toDateState.hide()
        }
    )

    /*var showAccountTypeDropDownMenu by remember {
        mutableStateOf(false)
    }*/

    var showAccountDropDownMenu by remember {
        mutableStateOf(false)
    }

    /*val accountTypeList = listOf<String>(
        "Customer", "SupplierLedgerReportScreenEvent", "Expense"
    )*/

    /*var selectedAccountType by remember {
        mutableStateOf("Customer")
    }*/

    //val accountType by salesViewModel

    val accountList = salesViewModel.accountList

    val selectedAccount by salesViewModel.selectedAccount

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = true) {
        salesViewModel.queryCustomerLedgerReportScreenEvent.collectLatest { value ->
            when (value.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }

                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }

                is UiEvent.Navigate -> {
                    saleNavHostController.navigate(value.uiEvent.route)
                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(message = value.uiEvent.message)
                }

                else -> Unit
            }
        }
    }





    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        modifier = Modifier.alpha(if (showProgressBar) 0.5f else 1.0f),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Customer Ledger Report",
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        saleNavHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top

        ) {
            Spacer(modifier = Modifier.height(60.dp))


            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Account",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    text = ":",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                ) {
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(width = 0.60.dp, color = Color(0xFF666464)),
                        shape = RoundedCornerShape(8)
                    ) {
                        TextButton(
                            onClick = {
                                showAccountDropDownMenu = true
                            },
                            enabled = !showProgressBar
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = selectedAccount?.name ?: "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(6f),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Start
                                )

                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = showAccountDropDownMenu,
                            onDismissRequest = {
                                showAccountDropDownMenu = false
                            },
                        )
                        {
                            accountList.forEach { account ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = account.name,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    onClick = {
                                        salesViewModel.setSelectedAccount(account)
                                        showAccountDropDownMenu = false
                                    }
                                )
                            }


                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date From",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    text = ":",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                ) {
                    TextField(
                        value = selectedFromDate.toString(),
                        onValueChange = {

                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    fromDateState.show()
                                },
                                enabled = !showProgressBar
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_calendar_month),
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }

            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date To",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = ":",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                ) {
                    TextField(
                        value = selectedToDate.toString(),
                        onValueChange = {

                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    toDateState.show()
                                },
                                enabled = !showProgressBar
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_calendar_month),
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }

            }
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        salesViewModel.getCustomerLedgerReport(
                            fromDate = selectedFromDate,
                            toDate = selectedToDate
                        )
                    },
                    enabled = !showProgressBar
                ) {
                    Text(text = "Show")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {
                        selectedFromDate = LocalDate.now().minusYears(1)
                        selectedToDate = LocalDate.now()
                        salesViewModel.setSelectedAccount(accountList[0])
                    },
                    enabled = !showProgressBar
                ) {
                    Text(text = "Clear")
                }
            }


        }
    }
    if (showProgressBar) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }


}