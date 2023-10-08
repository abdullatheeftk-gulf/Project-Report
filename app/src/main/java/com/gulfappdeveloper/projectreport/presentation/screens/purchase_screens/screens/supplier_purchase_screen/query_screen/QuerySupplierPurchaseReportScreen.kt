package com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.supplier_purchase_screen.query_screen

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.R
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.PurchaseViewModel
import com.gulfappdeveloper.projectreport.root.localDateToStringConverter
import com.gulfappdeveloper.projectreport.root.localTimeToStringConverter
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuerySupplierPurchaseReportScreen(
    purchaseViewModel: PurchaseViewModel,
    purchaseNavHostController: NavHostController
) {
    var selectedFromDate by remember {
        mutableStateOf<LocalDate>(LocalDate.now())
    }

    var selectedFromTime by remember {
        mutableStateOf(LocalTime.of(0,0))
    }


    var selectedToDate by remember {
        mutableStateOf<LocalDate>(LocalDate.now())
    }

    var selectedToTime by remember {
        mutableStateOf(LocalTime.of(23,59))
    }

    val fromDateState = rememberUseCaseState()
    val fromTimeState = rememberUseCaseState(embedded = false)

    val toDateState = rememberUseCaseState()
    val toTimeState = rememberUseCaseState(embedded = false)

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var showAccountDropDownMenu by remember {
        mutableStateOf(false)
    }

    val accountList = purchaseViewModel.accountList

    val selectedAccount by purchaseViewModel.selectedAccount



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

    ClockDialog(
        state = fromTimeState,
        config = ClockConfig(
            defaultTime = LocalTime.of(0,0),
            is24HourFormat = true
        ),
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            selectedFromTime = LocalTime.of(hours,minutes)
            fromTimeState.hide()
        },)

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

    ClockDialog(
        state = toTimeState,
        config = ClockConfig(defaultTime = LocalTime.of(23,59), is24HourFormat = true),
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            selectedToTime = LocalTime.of(hours,minutes)
            toDateState.hide()
        }
    )


    LaunchedEffect(key1 = true) {
        purchaseViewModel.querySupplierPurchaseReportScreenEvent.collectLatest { value ->
            when (value.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }

                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }

                is UiEvent.Navigate -> {
                    purchaseNavHostController.navigate(value.uiEvent.route)

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
        containerColor = Color(0xFFF5F5F5),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Supplier Purchase Report",
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        purchaseNavHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            )
        }) {
        it.calculateTopPadding()
     /*   Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = it.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Account",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    text = ":",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                ) {
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(width = 0.60.dp, color = Color(0xFF666464)),
                        shape = RoundedCornerShape(8)
                    ) {
                        TextButton(
                            onClick = {
                                showAccountDropDownMenu = true
                            },
                            enabled = !showProgressBar
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = selectedAccount?.name ?: "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(6f),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Start
                                )

                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = showAccountDropDownMenu,
                            onDismissRequest = {
                                showAccountDropDownMenu = false
                            },
                        )
                        {
                            accountList.forEach { account ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = account.name,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    onClick = {
                                        purchaseViewModel.setSelectedAccount(account)
                                        showAccountDropDownMenu = false
                                    }
                                )
                            }
                            
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
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
                        value = selectedFromDate.localDateToStringConverter(),
                        onValueChange = {

                        },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    fromDateState.show()
                                },
                                enabled = !showProgressBar
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_calendar_month),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
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
                        value = selectedToDate.localDateToStringConverter(),
                        onValueChange = {

                        },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    toDateState.show()
                                },
                                enabled = !showProgressBar
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_calendar_month),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
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
                        purchaseViewModel.getSupplierPurchaseReport(
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
        }*/

        /*Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = it.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // From card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "From",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp),
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Date", modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = ":",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedFromDate.localDateToStringConverter(),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                fromDateState.show()
                            },
                            enabled = !showProgressBar,
                            //modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_calendar_month),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Time", modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = ":",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedFromTime.localTimeToStringConverter(),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                fromTimeState.show()
                            },
                            enabled = !showProgressBar,
                            //modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_access_time_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }


            }

            Spacer(modifier = Modifier.height(16.dp))

            // To Date card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "To",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp),
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Date", modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = ":",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedToDate.localDateToStringConverter(),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                toDateState.show()
                            },
                            enabled = !showProgressBar,
                            //modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_calendar_month),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Time", modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = ":",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedToTime.localTimeToStringConverter(),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                toTimeState.show()
                            },
                            enabled = !showProgressBar,
                            //modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_access_time_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }


            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        purchaseViewModel.getSupplierPurchaseReport(
                            fromDate = selectedFromDate,
                            fromTime = selectedFromTime,
                            toDate = selectedToDate,
                            toTime = selectedToTime
                        )
                    },
                    enabled = !showProgressBar
                ) {
                    Text(text = "Show")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {
                        selectedFromDate = LocalDate.now()
                        selectedFromTime = LocalTime.of(0,0)

                        selectedToDate = LocalDate.now()
                        selectedToTime = LocalTime.of(23,59)
                    },
                    enabled = !showProgressBar
                ) {
                    Text(text = "Clear")
                }
            }
        }*/

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = it.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top

        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Account",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    text = ":",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)
                ) {
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(
                            width = 0.60.dp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(8),
                        colors = CardDefaults.cardColors()
                    ) {
                        TextButton(
                            onClick = {
                                showAccountDropDownMenu = true
                            },
                            enabled = !showProgressBar
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = selectedAccount?.name ?: "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(6f),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Start
                                )

                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = showAccountDropDownMenu,
                            onDismissRequest = {
                                showAccountDropDownMenu = false
                            },
                        )
                        {
                            accountList.forEach { account ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = account.name,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    onClick = {
                                        //salesViewModel.setSelectedAccount(account)
                                        purchaseViewModel.setSelectedAccount(value = account)
                                        showAccountDropDownMenu = false
                                    }
                                )
                            }


                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "From",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp),
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Date", modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = ":",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedFromDate.localDateToStringConverter(),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                fromDateState.show()
                            },
                            enabled = !showProgressBar,
                            //modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_calendar_month),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Time", modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = ":",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedFromTime.localTimeToStringConverter(),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                fromTimeState.show()
                            },
                            enabled = !showProgressBar,
                            //modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_access_time_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }


            }

            Spacer(modifier = Modifier.height(16.dp))

            // To Date card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "To",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp),
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Date", modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = ":",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedToDate.localDateToStringConverter(),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                toDateState.show()
                            },
                            enabled = !showProgressBar,
                            //modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_calendar_month),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Time", modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = ":",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedToTime.localTimeToStringConverter(),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                toTimeState.show()
                            },
                            enabled = !showProgressBar,
                            //modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_access_time_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }


            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {

                        purchaseViewModel.getSupplierPurchaseReport(
                            fromDate = selectedFromDate,
                            fromTime = selectedFromTime,
                            toDate = selectedToDate,
                            toTime = selectedToTime
                        )
                    },
                    enabled = !showProgressBar
                ) {
                    Text(text = "Show")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {
                        selectedFromDate = LocalDate.now()
                        selectedFromTime = LocalTime.of(0, 0)

                        selectedToDate = LocalDate.now()
                        selectedToTime = LocalTime.of(23, 59)
                    },
                    enabled = !showProgressBar
                ) {
                    Text(text = "Clear")
                }
            }

            /*Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date From",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    text = ":",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                ) {
                    TextField(
                        value = selectedFromDate.localDateToStringConverter(),
                        onValueChange = {

                        },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    fromDateState.show()
                                },
                                enabled = !showProgressBar
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_calendar_month),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    )
                }

            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date To",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = ":",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                ) {
                    TextField(
                        value = selectedToDate.localDateToStringConverter(),
                        onValueChange = {

                        },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    toDateState.show()
                                },
                                enabled = !showProgressBar
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_calendar_month),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
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
                        salesViewModel.getCustomerLedgerReport(
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
                        salesViewModel.setSelectedAccount(accountList[0])
                    },
                    enabled = !showProgressBar
                ) {
                    Text(text = "Clear")
                }
            }*/


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