package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.customer_ledger_screens.report_screen

import android.content.Intent
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.SalesViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.customer_ledger_screens.report_screen.components.CustomerLedgerReportTable
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.pos_payment_report_screen.report_screen.ScreenOrientationActionForPosPayment
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerLedgerReportScreen(
    saleNavHostController: NavHostController,
    salesViewModel: SalesViewModel
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val partyName by salesViewModel.partyName
    val balance by salesViewModel.balance

    val fromDate by salesViewModel.fromDateState
    val fromTime by salesViewModel.fromTimeState

    val toDate by salesViewModel.toDateState
    val toTime by salesViewModel.toTimeState



    val reArrangedCustomerLedgerReportList = salesViewModel.reArrangedCustomerLedgerReportList
    val customerLedgerReportTotals by salesViewModel.customerLedgerReportTotals

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var expandMenu by remember {
        mutableStateOf(false)
    }



    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        salesViewModel.customerLedgerReportScreenEvent.collectLatest {value->
            when(value.uiEvent){
                is UiEvent.ShowProgressBar->{
                    
                    showProgressBar = true
                }
                is UiEvent.CloseProgressBar->{
                    showProgressBar = false
                }
                is UiEvent.ShowSnackBar->{
                    snackBarHostState.showSnackbar(value.uiEvent.message)
                }
                else->Unit
            }
        }
    }



    Scaffold(
        snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    },
        modifier = Modifier.alpha(if (showProgressBar) 0.5f else 1f),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row {
                        Text(
                            text = "Customer Ledger\nReport",
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

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
                },actions = {
                    ScreenOrientationActionForPosPayment(
                        context = context,
                        salesViewModel = salesViewModel
                    )
                    Box(
                        modifier = Modifier
                            //.fillMaxWidth()
                            .wrapContentSize(Alignment.TopEnd)
                    ) {
                        IconButton(onClick = {
                            expandMenu = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        DropdownMenu(
                            expanded = expandMenu,
                            onDismissRequest = { expandMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = "PDF", color = Color.Red) },
                                onClick = {
                                    salesViewModel.makePDFForCustomerLedgerReport {
                                        val shareIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(Intent.EXTRA_STREAM, it)
                                            type = "application/pdf"
                                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                                        }

                                        context.startActivity(
                                            Intent.createChooser(
                                                shareIntent,
                                                null,
                                            )
                                        )
                                    }
                                    expandMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(text = "EXCEL", color = Color(0xFF017706)) },
                                onClick = {
                                    salesViewModel.makeExcelForCustomerLedgerReport {
                                        val shareIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(Intent.EXTRA_STREAM, it)
                                            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                                        }

                                        context.startActivity(
                                            Intent.createChooser(
                                                shareIntent,
                                                null,
                                            )
                                        )
                                    }
                                    expandMenu = false
                                }
                            )
                        }
                    }

                }
            )
        }
    ) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    start = 4.dp,
                    end = 4.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Name",
                    modifier = Modifier
                        // .fillMaxWidth()
                        .weight(0.5f)
                )
                Text(
                    text = ":",
                    modifier = Modifier
                        // .fillMaxWidth()
                        .weight(.1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = partyName,
                    modifier = Modifier
                        //.fillMaxWidth()
                        .weight(2f),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Balance",
                    modifier = Modifier
                        .weight(0.5f)
                )
                Text(
                    text = " :",
                    modifier = Modifier
                        .weight(.1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = balance.toString(),
                    modifier = Modifier
                        .weight(2f),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Period",
                    modifier = Modifier
                        //.fillMaxWidth()
                        .weight(0.5f)
                )
                Text(
                    text = ":",
                    modifier = Modifier
                        //.fillMaxWidth()
                        .weight(.1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("$fromDate, $fromTime")
                        }
                        append(" to ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("$toDate, $toTime")
                        }
                    },
                    modifier = Modifier.weight(2f),
                )

            }
            Spacer(modifier = Modifier.height(25.dp))

            if (reArrangedCustomerLedgerReportList.isEmpty()) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "Empty Ledger Report",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                    modifier = Modifier.alpha(0.5f)
                )

            } else {
                    CustomerLedgerReportTable(customerLedgerDetails = reArrangedCustomerLedgerReportList, customerLedgerTotals =customerLedgerReportTotals!! )
            }
        }

    }
    if (showProgressBar){

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}