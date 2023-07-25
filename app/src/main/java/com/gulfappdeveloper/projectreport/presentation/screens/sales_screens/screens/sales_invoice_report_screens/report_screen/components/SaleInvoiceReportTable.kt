package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_invoice_report_screens.report_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gulfappdeveloper.projectreport.domain.models.sales.SalesInvoiceResponse
import com.gulfappdeveloper.projectreport.root.stringToDateStringConverter
import eu.wewox.lazytable.LazyTable
import eu.wewox.lazytable.LazyTableItem
import eu.wewox.lazytable.lazyTableDimensions
import eu.wewox.lazytable.lazyTablePinConfiguration

@Composable
fun SaleInvoiceReportTable(
    saleInvoiceReportList: SnapshotStateList<SalesInvoiceResponse>
) {
    val totalColumn = 9
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
            count = saleInvoiceReportList.size * totalColumn,
            layoutInfo = {
                LazyTableItem(
                    column = it % totalColumn,
                    row = it / totalColumn + 1
                )
            }
        ) {
            val rowCount = it / totalColumn + 1
            val columCont = it % totalColumn


            val rowData = saleInvoiceReportList[rowCount - 1]
            val content = when (columCont) {
                0 -> "$rowCount"
                1 -> rowData.date.stringToDateStringConverter()
                2 -> rowData.invoiceNo.toString()
                3 -> rowData.party ?: "-"
                4 -> rowData.taxable.toString()
                5 -> rowData.tax.toString()
                6 -> rowData.returnTaxable.toString()
                7 -> rowData.taxOnReturn.toString()
                8 -> rowData.net.toString()
                else -> "Error"
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(if (rowCount % 2 == 0) Color(0xFFEDE6E6) else MaterialTheme.colorScheme.background)
                    .border(Dp.Hairline, Color.Black)
            ) {
                if (columCont % totalColumn == 1) {
                    Text(
                        text = content,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 4.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
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
            count = totalColumn,
            layoutInfo = {
                LazyTableItem(
                    column = it % totalColumn,
                    row = 0,
                )
            },
        ) {
            val content = when (it) {
                0 -> "Si"
                1 -> "Date"
                2 -> "Receipt No"
                3 -> "Account"
                4 -> "Taxable"
                5 -> "Tax"
                6 -> "Return"
                7 -> "Tax on return"
                8 -> "Net"
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