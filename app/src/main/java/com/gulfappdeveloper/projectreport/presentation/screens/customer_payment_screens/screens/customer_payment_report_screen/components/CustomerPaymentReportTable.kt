package com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens.screens.customer_payment_report_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import eu.wewox.lazytable.LazyTable
import eu.wewox.lazytable.LazyTableItem
import eu.wewox.lazytable.lazyTableDimensions
import eu.wewox.lazytable.lazyTablePinConfiguration

private const val TAG = "NewTable"

@Composable
fun CustomerPaymentReportTable(customerPaymentReportList: SnapshotStateList<CustomerPaymentResponse>) {

    LazyTable(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black),
        pinConfiguration = lazyTablePinConfiguration(
            columns = {
                0
            },
            rows = {
                1
            }
        ),
        dimensions = lazyTableDimensions(
            columnSize = {
                val value = when (it) {
                    0 -> 44.dp
                    1 -> 100.dp
                    2 -> 64.dp
                    3 -> 200.dp
                    4 -> 92.dp
                    6 -> 92.dp
                    7 -> 92.dp
                    8 -> 92.dp
                    else -> 92.dp
                }
                value

            },
            rowSize = {
                if (it == 0) 48.dp else 44.dp
            }
        )
    ) {

        items(
            count = customerPaymentReportList.size * 9,
            layoutInfo = {
                LazyTableItem(
                    column = it % 9,
                    row = it / 9 + 1
                )
            }
        ) {
            val rowCount = it / 9 + 1
            val columCont = it % 9


            val rowData = customerPaymentReportList[rowCount - 1]
            val content = when (columCont) {
                0 -> "$rowCount"
                1-> rowData.date
                2-> rowData.receiptNo.toString()
                3-> rowData.party
                4-> rowData.cash.toString()
                5-> rowData.card.toString()
                6-> rowData.online.toString()
                7-> rowData.credit.toString()
                8->rowData.total.toString()
                else->"Error"
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .border(Dp.Hairline, Color.Black)
            ) {
                if (columCont%9==1){
                    Text(
                        text = content,
                        modifier = Modifier.wrapContentSize().padding(horizontal = 4.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 14.sp
                    )
                }else {
                    Text(
                        text = content,
                        modifier = Modifier.wrapContentSize().padding(horizontal = 4.dp),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

        }


        items(
            count = 9,
            layoutInfo = {
                LazyTableItem(
                    column = it % 9,
                    row = 0,
                )
            },
        ) {
            val content = when (it) {
                0 -> {
                    "Si"
                }

                1 -> {
                    "Date"
                }

                2 -> {
                    "Rec.No"
                }

                3 -> {
                    "Party"
                }

                4 -> {
                    "Cash"
                }

                5 -> {
                    "Card"
                }

                6 -> {
                    "Online"
                }

                7 -> {
                    "Credit"
                }

                8 -> {
                    "Total"
                }

                else -> "Error"
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color(0xFFB7B4B4))
                    .border(Dp.Hairline, Color.Black)
            ) {
                Text(
                    text = content,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                )
            }
        }

    }
}