package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.pos_payment_report_screen.query_screen

import android.app.Activity
import android.content.pm.ActivityInfo
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.R
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.SalesViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueryPosPaymentReportScreen(
    salesViewModel:SalesViewModel,
    salesNavHostController:NavHostController
) {

    var selectedFromDate by remember {
        mutableStateOf<LocalDate>(LocalDate.now().minusYears(1))
    }
    var selectedToDate by remember {
        mutableStateOf<LocalDate>(LocalDate.now())
    }

    val fromDateState = rememberUseCaseState()
    val toDateState = rememberUseCaseState()

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    var showProgressBar by remember {
        mutableStateOf(false)
    }



    CalendarDialog(
        state = fromDateState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date(
            selectedDate = selectedFromDate
        ) { newDate ->
            selectedFromDate = newDate
            fromDateState.hide()
        }
    )

    CalendarDialog(
        state = toDateState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date(
            selectedDate = selectedToDate
        ) { newDate ->
            selectedToDate = newDate
            toDateState.hide()
        }
    )


    LaunchedEffect(key1 = true) {
        salesViewModel.queryPosPaymentReportScreenEvent.collectLatest { value ->
            when (value.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }

                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }

                is UiEvent.Navigate -> {
                     salesNavHostController.navigate(value.uiEvent.route)

                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(value.uiEvent.message)
                }

                else -> Unit
            }
        }
    }

    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    },
        modifier = Modifier.alpha(if (showProgressBar) 0.5f else 1.0f),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pos Payment Report",
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
        }) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "From Date", modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    text = ":",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                ) {
                    TextField(
                        value = selectedFromDate.toString(),
                        onValueChange = {

                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    fromDateState.show()
                                },
                                enabled = !showProgressBar
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_calendar_month),
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "To Date", modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    text = ":",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                ) {
                    TextField(
                        value = selectedToDate.toString(),
                        onValueChange = {

                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    toDateState.show()
                                },
                                enabled = !showProgressBar
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_calendar_month),
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        salesViewModel.getPosPaymentReport(
                            fromDate = selectedFromDate,
                            toDate = selectedToDate
                        )
                    },
                    enabled = !showProgressBar
                ) {
                    Text(text = "Show")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {
                        selectedFromDate = LocalDate.now().minusYears(1)
                        selectedToDate = LocalDate.now()
                    },
                    enabled = !showProgressBar
                ) {
                    Text(text = "Clear")
                }
            }
        }

    }
    if (showProgressBar) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}