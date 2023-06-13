package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sale_summaries_report_screens.report_screen.components

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
import com.gulfappdeveloper.projectreport.domain.models.sales.SaleSummariesResponse
import eu.wewox.lazytable.LazyTable
import eu.wewox.lazytable.LazyTableItem
import eu.wewox.lazytable.lazyTableDimensions
import eu.wewox.lazytable.lazyTablePinConfiguration

@Composable
fun SaleSummaryReportTable(
    saleSummaryReportList:SnapshotStateList<SaleSummariesResponse>,
) {
    val totalColumn = 7
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
            count = saleSummaryReportList.size * totalColumn,
            layoutInfo = {
                LazyTableItem(
                    column = it % totalColumn,
                    row = it / totalColumn + 1
                )
            }
        ) {
            val rowCount = it / totalColumn + 1
            val columCont = it % totalColumn


            val rowData = saleSummaryReportList[rowCount - 1]
            val content = when (columCont) {
                0 -> "$rowCount"
                1-> rowData.date
                2-> rowData.taxable.toString()
                3-> rowData.tax.toString()
                4-> rowData.returnTaxable.toString()
                5-> rowData.returnTax.toString()
                6-> rowData.net.toString()
                else->"Error"
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(if(rowCount%2==0) Color(0xFFEDE6E6) else MaterialTheme.colorScheme.background )
                    .border(Dp.Hairline, Color.Black)
            ) {
                if (columCont%totalColumn==1){
                    Text(
                        text = content,
                        modifier = Modifier.wrapContentSize().padding(horizontal = 4.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 14.sp,
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
            count = totalColumn,
            layoutInfo = {
                LazyTableItem(
                    column = it % totalColumn,
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
                    "Taxable"
                }
                3->{
                    "Tax"
                }

                4 -> {
                    "Return Taxable"
                }

                5 -> {
                    "Return Tax"
                }

                6 -> {
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

    }
}