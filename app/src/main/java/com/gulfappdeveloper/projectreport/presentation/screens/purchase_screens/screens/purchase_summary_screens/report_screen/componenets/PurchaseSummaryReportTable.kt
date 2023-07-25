package com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.purchase_summary_screens.report_screen.componenets

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseSummaryResponse
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseSummaryTotals
import com.gulfappdeveloper.projectreport.root.stringToDateStringConverter
import eu.wewox.lazytable.LazyTable
import eu.wewox.lazytable.LazyTableItem
import eu.wewox.lazytable.lazyTableDimensions
import eu.wewox.lazytable.lazyTablePinConfiguration

@Composable
fun PurchaseSummaryReportTable(
    purchaseSummaryReportList: List<PurchaseSummaryResponse>,
    purchaseSummaryTotals: PurchaseSummaryTotals
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
                    5 -> 102.dp
                    6 -> 102.dp
                    7 -> 102.dp
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
            count = purchaseSummaryReportList.size * 8,
            layoutInfo = {
                LazyTableItem(
                    column = it % 8,
                    row = it / 8 + 1
                )
            }
        ) {
            val rowCount = it / 8 + 1
            val columCont = it % 8


            val rowData = purchaseSummaryReportList[rowCount - 1]
            val content = when (columCont) {
                0 -> "$rowCount"
                1 -> rowData.invoiceDate.stringToDateStringConverter()
                2 -> rowData.invoiceNo.toString()
                3 -> rowData.supplier ?: ""
                4 -> rowData.taxId ?: ""
                5 -> rowData.taxable.toString()
                6 -> rowData.tax.toString()
                7 -> rowData.net.toString()


                else -> "Error"
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(if (rowCount % 2 == 0) Color(0xFFEDE6E6) else MaterialTheme.colorScheme.background)
                    .border(Dp.Hairline, Color.Black)
            ) {
                if (columCont % 8 == 1) {
                    Text(
                        text = content,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 4.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                        //color = if ()
                    )
                } else {
                    Text(
                        text = content,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 4.dp),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

        }


        items(
            count = 8,
            layoutInfo = {
                LazyTableItem(
                    column = it % 8,
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
                    "Supplier"
                }
                4->{
                    "Tax Id"
                }

                5 -> {
                    "Taxable"
                }

                6 -> {
                    "Tax"
                }

                7-> {
                    "Net"
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
            count = 8,
            layoutInfo = {
                LazyTableItem(
                    column = it % 10,
                    row = purchaseSummaryReportList.size + 1
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
                            text = "",
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
                            //.border(Dp.Hairline, Color.Black)
                    ) {
                        Text(
                            text = "Total :- ",
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
                            text = String.format("%.2f", purchaseSummaryTotals.sumOfTaxable),
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
                            text = String.format("%.2f", purchaseSummaryTotals.sumOfTax),
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
                            text = String.format(
                                "%.2f",
                                purchaseSummaryTotals.sumOfNet
                            ),
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