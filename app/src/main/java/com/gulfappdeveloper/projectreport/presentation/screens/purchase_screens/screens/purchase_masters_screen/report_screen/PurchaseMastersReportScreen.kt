package com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.purchase_masters_screen.report_screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.projectreport.R
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.PurchaseViewModel
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.purchase_masters_screen.report_screen.components.PurchaseMastersReportTable
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "PurchaseMastersReportSc"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseMastersReportScreen(
    purchaseNavHostController: NavHostController,
    purchaseViewModel: PurchaseViewModel
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val fromDate by purchaseViewModel.fromDateState
    val toDate by purchaseViewModel.toDateState

    val purchaseMastersReportList = purchaseViewModel.purchaseMastersReportList
    val purchaseMastersReportTotals by purchaseViewModel.purchaseMastersReportTotal

    var expandMenu by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val orientation by remember {
        mutableStateOf(context.resources.configuration.orientation)
    }
    purchaseViewModel.setOrientation(orientation)

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        purchaseViewModel.purchaseMastersReportScreenEvent.collectLatest { value ->
            when (value.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }

                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(value.uiEvent.message)
                }

                else -> Unit
            }

        }
    }
    if (showProgressBar){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        modifier = Modifier.alpha(if (showProgressBar) 0.5f else 1.0f),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Purchase Masters Report",
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
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
                actions = {
                    ScreenOrientationActionForPurchaseMasters(context = context, purchaseViewModel = purchaseViewModel)
                    Box(
                        modifier = Modifier
                            //.fillMaxWidth()
                            .wrapContentSize(Alignment.TopEnd)
                    ) {
                        IconButton(onClick = {
                            expandMenu = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        DropdownMenu(
                            expanded = expandMenu,
                            onDismissRequest = { expandMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = "PDF", color = Color.Red) },
                                onClick = {
                                    /*salesViewModel.makePdfForPosPaymentReport {
                                        val shareIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(Intent.EXTRA_STREAM, it)
                                            type = "application/pdf"
                                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                                        }

                                        context.startActivity(
                                            Intent.createChooser(
                                                shareIntent,
                                                null,
                                            )
                                        )
                                    }*/
                                    expandMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(text = "EXCEL", color = Color(0xFF017706)) },
                                onClick = {
                                    /*Todo*/
                                    expandMenu = false
                                }
                            )
                        }
                    }

                }
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

            if (purchaseMastersReportList.isEmpty()) {
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
                } catch (e: Exception) {
                    Log.e(TAG, "PosPaymentReportScreen: $e")
                }

            }
            PurchaseMastersReportTable(
                purchaseMastersReportList = purchaseMastersReportList,
                purchaseMastersReportListTotals = purchaseMastersReportTotals!!
            )

        }
    }



}

@Composable
fun ScreenOrientationActionForPurchaseMasters(
    context: Context,
    purchaseViewModel: PurchaseViewModel
) {
    val activity = context as Activity
    val portrait by purchaseViewModel.orientation
    IconButton(onClick = {

        if (portrait) {

            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {

            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }

    }) {
        Icon(
            painter = painterResource(
                id = R.drawable.baseline_screen_rotation_24
            ),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}