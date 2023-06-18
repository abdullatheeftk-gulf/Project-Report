package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.customer_ledger_screens.report_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.SalesViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.customer_ledger_screens.report_screen.components.CustomerLedgerReportTable

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CustomerLedgerReportScreen(
    saleNavHostController: NavHostController,
    salesViewModel: SalesViewModel
) {

    val partyName by salesViewModel.partyName
    val balance by salesViewModel.balance

    val fromDate by salesViewModel.fromDateState
    val toDate by salesViewModel.toDateState


    //val customerLedgerReportList = salesViewModel.customerLedgerReportList

    val reArrangedCustomerLedgerReportList = salesViewModel.reArrangedCustomerLedgerReportList
    val customerLedgerReportTotals by salesViewModel.customerLedgerReportTotals

    Scaffold(
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
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.height(60.dp))
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
                verticalAlignment = Alignment.CenterVertically
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
                Row(
                    modifier = Modifier
                        //.fillMaxWidth()
                        .weight(2f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = fromDate,
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp
                    )
                    Text(
                        text = " to ",
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                    )
                    Text(
                        text = toDate,
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp
                    )
                }

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
}