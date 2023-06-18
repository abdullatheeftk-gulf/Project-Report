package com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.supplier_purchase_screen.report_screen.components

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
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseMastersResponse
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseMasterTotals
import eu.wewox.lazytable.LazyTable
import eu.wewox.lazytable.LazyTableItem
import eu.wewox.lazytable.lazyTableDimensions
import eu.wewox.lazytable.lazyTablePinConfiguration

@Composable
fun SupplierPurchaseReportTable(
    supplierPurchaseReportList: List<PurchaseMastersResponse>,
    supplierPurchaseReportTotals: PurchaseMasterTotals
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
                    8 -> 102.dp
                    9 -> 140.dp
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
            count = supplierPurchaseReportList.size * 10,
            layoutInfo = {
                LazyTableItem(
                    column = it % 10,
                    row = it / 10 + 1
                )
            }
        ) {
            val rowCount = it / 10 + 1
            val columCont = it % 10


            val rowData = supplierPurchaseReportList[rowCount - 1]
            val content = when (columCont) {
                0 -> "$rowCount"
                1 -> rowData.invoiceDate
                2 -> rowData.invoiceNo.toString()
                3 -> rowData.supplier
                4 -> rowData.taxable.toString()
                5 -> rowData.tax.toString()
                6 -> rowData.net.toString()
                7 -> rowData.payment.toString()
                8 -> rowData.returnAmount.toString()
                9 -> rowData.balanceAmount.toString()
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
                    "Supplier"
                }

                4 -> {
                    "Taxable"
                }

                5 -> {
                    "Tax"
                }

                6 -> {
                    "Net"
                }

                7 -> {
                    "Payment"
                }

                8 -> {
                    "Return Amt"
                }

                9 -> {
                    "Balance Amt"
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
                    row = supplierPurchaseReportList.size + 1
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
                            text = String.format("%.2f", supplierPurchaseReportTotals.sumOfTaxable),
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
                            text = String.format("%.2f", supplierPurchaseReportTotals.sumOfTax),
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
                            text = String.format("%.2f", supplierPurchaseReportTotals.sumOfNet),
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
                            text = String.format("%.2f", supplierPurchaseReportTotals.sumOfPayment),
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
                            text = String.format(
                                "%.2f",
                                supplierPurchaseReportTotals.sumOfReturnAmount
                            ),
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
                            text = String.format(
                                "%.2f",
                                supplierPurchaseReportTotals.sumOfBalanceAmount
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