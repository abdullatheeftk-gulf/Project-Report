package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.pos_payment_report_screen.report_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import eu.wewox.lazytable.LazyTable
import eu.wewox.lazytable.LazyTableItem
import eu.wewox.lazytable.lazyTableDimensions
import eu.wewox.lazytable.lazyTablePinConfiguration

@Composable
fun PosPaymentReportTable(
    posPaymentReportList: List<PosPaymentResponse>,
    posPaymentReportListTotalList: List<Double>
) {
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
                    4 -> 102.dp
                    6 -> 102.dp
                    7 -> 102.dp
                    8 -> 102.dp
                    9 -> 102.dp
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
            count = posPaymentReportList.size * 10,
            layoutInfo = {
                LazyTableItem(
                    column = it % 10,
                    row = it / 10 + 1
                )
            }
        ) {
            val rowCount = it / 10 + 1
            val columCont = it % 10


            val rowData = posPaymentReportList[rowCount - 1]
            val content = when (columCont) {
                0 -> "$rowCount"
                1 -> rowData.date
                2 -> rowData.invoiceNo.toString()
                3 -> rowData.party
                4 -> rowData.cash.toString()
                5 -> rowData.card.toString()
                6 -> rowData.onlineAmount.toString()
                7 -> rowData.credit.toString()
                8 -> rowData.returnAmount.toString()
                9 -> rowData.total.toString()
                else -> "Error"
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(if (rowCount % 2 == 0) Color(0xFFEDE6E6) else MaterialTheme.colorScheme.background)
                    .border(Dp.Hairline, Color.Black)
            ) {
                if (columCont % 10 == 1) {
                    Text(
                        text = content ?: "-",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 4.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 14.sp,
                        //color = if ()
                    )
                } else {
                    Text(
                        text = content ?: "-",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 4.dp),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

        }


        items(
            count = 10,
            layoutInfo = {
                LazyTableItem(
                    column = it % 10,
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
                    "Inv.No"
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
                8->{
                    "Return Amt"
                }

                9 -> {
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

        items(
            count = 10,
            layoutInfo = {
                LazyTableItem(
                    column = it % 10,
                    row = posPaymentReportList.size + 1
                )
            }
        ) {
            when (it) {
                0 -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color(0xFFFFFFFF))
                        //.border(Dp.Hairline, Color.Black)
                    ) {
                        Text(
                            text = "",
                            modifier = Modifier.padding(horizontal = 4.dp),
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                        )
                    }
                }

                1 -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color(0xFFFFFFFF))
                        //.border(Dp.Hairline, Color.Black)
                    ) {
                        Text(
                            text = "",
                            modifier = Modifier.padding(horizontal = 4.dp),
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                        )
                    }
                }

                2 -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color(0xFFFFFFFF))
                        //.border(Dp.Hairline, Color.Black)
                    ) {
                        Text(
                            text = "",
                            modifier = Modifier.padding(horizontal = 4.dp),
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                        )
                    }
                }

                3 -> {
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .background(Color(0xFFFFFFFF))
                        //.border(Dp.Hairline, Color.Black)
                    ) {
                        Text(
                            text = "Total :- ",
                            modifier = Modifier.padding(horizontal = 4.dp),
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                        )
                    }
                }

                4 -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color(0xFFFFFFFF))
                            .border(Dp.Hairline, Color.Black)
                    ) {
                        Text(
                            text = String.format("%.2f", posPaymentReportListTotalList[0]),
                            modifier = Modifier.padding(horizontal = 4.dp),
                            fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                        )
                    }
                }

                5 -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color(0xFFFFFFFF))
                            .border(Dp.Hairline, Color.Black)
                    ) {
                        Text(
                            text = String.format("%.2f", posPaymentReportListTotalList[1]),
                            modifier = Modifier.padding(horizontal = 4.dp),
                            fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                        )
                    }
                }

                6 -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color(0xFFFFFFFF))
                            .border(Dp.Hairline, Color.Black)
                    ) {
                        Text(
                            text = String.format("%.2f", posPaymentReportListTotalList[2]),
                            modifier = Modifier.padding(horizontal = 4.dp),
                            fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                        )
                    }
                }

                7 -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color(0xFFFFFFFF))
                            .border(Dp.Hairline, Color.Black)
                    ) {
                        Text(
                            text = String.format("%.2f", posPaymentReportListTotalList[3]),
                            modifier = Modifier.padding(horizontal = 4.dp),
                            fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                        )
                    }
                }

                8 -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color(0xFFFFFFFF))
                            .border(Dp.Hairline, Color.Black)
                    ) {
                        Text(
                            text = String.format("%.2f", posPaymentReportListTotalList[4]),
                            modifier = Modifier.padding(horizontal = 4.dp),
                            fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                        )
                    }
                }
                9 -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color(0xFFFFFFFF))
                            .border(Dp.Hairline, Color.Black)
                    ) {
                        Text(
                            text = String.format("%.2f", posPaymentReportListTotalList[5]),
                            modifier = Modifier.padding(horizontal = 4.dp),
                            fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                        )
                    }
                }

                else -> Unit
            }

        }

    }
}