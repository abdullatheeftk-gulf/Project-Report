package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.user_sales_report_screens.report_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.SalesViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.user_sales_report_screens.report_screen.components.UserSalesItemTableCell

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun UserSalesReportScreen(
    salesNavHostController: NavHostController,
    salesViewModel: SalesViewModel
) {
    val fromDate by salesViewModel.fromDateState
    val toDate by salesViewModel.toDateState

    val userSalesReportList = salesViewModel.userSalesReportList


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "User Sales Report",
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        salesNavHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },

            )
        }
    ) {
        it.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            Row(modifier = Modifier.fillMaxWidth()) {

                Text(text = "Report from ")
                Text(text = fromDate, color = MaterialTheme.colorScheme.primary, fontSize = 17.sp)
                Text(text = " to ")
                Text(text = toDate, color = MaterialTheme.colorScheme.primary, fontSize = 17.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))

            if (userSalesReportList.isEmpty()){
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Empty List",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.alpha(.5f),
                        fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                        fontWeight = MaterialTheme.typography.headlineMedium.fontWeight
                    )
                }
                try {
                    return@Scaffold
                }catch (_:Exception){

                }

            }
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(0),
                border = BorderStroke(width = 1.dp, color = Color(0xFF353232))
            ) {
                LazyColumn {
                    val userNameWeight = 0.5f
                    val salesAmountWeight = 0.5f
                    stickyHeader {

                        Row(modifier = Modifier.background(Color(0xFFB7B4B4))) {
                            UserSalesItemTableCell(
                                weight = userNameWeight,
                                text = "User Name",
                            )
                            UserSalesItemTableCell(
                                weight = salesAmountWeight,
                                text = "Sales Amount",
                            )
                        }
                    }



                    itemsIndexed(userSalesReportList) { index, userSale ->
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
                            UserSalesItemTableCell(
                                weight = userNameWeight,
                                text = userSale.userName,
                                fontSize = 14.sp
                            )
                            UserSalesItemTableCell(
                                weight = salesAmountWeight,
                                text = userSale.salesAmount.toString(),
                            )

                        }
                    }
                }
            }
        }
    }
}