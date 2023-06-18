package com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.screens.ledger_report_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.screens.ledger_report_screen.componenets.TableCell
import com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.LedgerReportViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.screens.ledger_report_screen.componenets.ItemTableCell

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LedgerReportScreen(
    ledgerReportViewModel: LedgerReportViewModel,
    ledgerNavHostController: NavHostController
) {

    val partyName by ledgerReportViewModel.partyName
    val balance by ledgerReportViewModel.balance

    val fromDate by ledgerReportViewModel.fromDateToDisplay
    val toDate by ledgerReportViewModel.toDateToDisplay

    val selectedAccountType by ledgerReportViewModel.selectedAccountType

    val ledgerList = ledgerReportViewModel.ledgerList


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (selectedAccountType == "Customer") "Customer Ledger Report" else if(selectedAccountType == "SupplierLedgerReportScreenEvent") "Expense Ledger Report" else "Expense Report",
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        ledgerNavHostController.popBackStack()
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
            if (ledgerList.isEmpty()) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "Empty Ledger Report",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                    modifier = Modifier.alpha(0.5f)
                )

            } else {
                OutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(0),
                    border = BorderStroke(width = 1.dp, color = Color(0xFF353232))
                ) {
                    LazyColumn {
                        val dateWeight = 0.25f
                        //val voucherNoWeight = 0.2f
                        val amountWeight = 0.2f
                        val particularsWeight = 0.35f
                        stickyHeader {

                            Row(modifier = Modifier.background(Color(0xFFB7B4B4))) {
                                TableCell(
                                    weight = dateWeight,
                                    text = "Date    ",
                                )

                                TableCell(
                                    weight = particularsWeight,
                                    text = "Particulars",
                                )
                                TableCell(
                                    weight = amountWeight,
                                    text = "Amount",
                                )

                            }
                        }



                        itemsIndexed(ledgerList) { index, ledgerDetail ->
                            Row(
                                modifier = Modifier.background(
                                    if (index % 2 == 0)
                                        MaterialTheme.colorScheme.background
                                    else
                                        Color(0xFFEDE6E6)
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                ItemTableCell(
                                    weight = dateWeight,
                                    text = ledgerDetail.vchrDate,
                                    textColor = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 14.sp
                                )
                                ItemTableCell(
                                    weight = particularsWeight,
                                    text = ledgerDetail.particulars,
                                    textColor = MaterialTheme.colorScheme.onBackground,
                                )
                                val s = if (ledgerDetail.vchrType == "Debit") "+ " else "- "
                                ItemTableCell(
                                    weight = amountWeight,
                                    text = s + ledgerDetail.amount.toString(),
                                    textColor = if (ledgerDetail.vchrType == "Debit") Color(
                                        0xFF54AB57
                                    ) else Color.Red
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}

/*
@Preview
@Composable
fun LedgerReportScreenPrev() {
    LedgerReportScreen()
}*/
