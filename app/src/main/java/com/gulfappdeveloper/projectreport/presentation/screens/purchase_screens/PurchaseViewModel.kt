package com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.projectreport.domain.models.general.Error
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.ledger.GetCustomerForLedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseMastersResponse
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseSummaryResponse
import com.gulfappdeveloper.projectreport.domain.models.purchase.supplier_ledger_report.Detail
import com.gulfappdeveloper.projectreport.domain.services.FirebaseService
import com.gulfappdeveloper.projectreport.presentation.screen_util.PresentationConstants
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.navigation.PurchaseScreens
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseMasterSelection
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseMasterTotals
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseSummaryTotals
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.ReArrangedSupplierLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.SupplierLedgerTotals
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.purchase_masters_screen.query_screen.util.QueryPurchaseMastersReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.purchase_masters_screen.report_screen.util.PurchaseMastersReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.purchase_summary_screens.query_screen.util.QueryPurchaseSummaryReportScreenUiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.purchase_summary_screens.report_screen.util.PurchaseSummaryReportScreenUiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.supplier_ledger_screen.query_screen.util.QuerySupplierLedgerReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.supplier_ledger_screen.report_screen.util.SupplierLedgerReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.supplier_purchase_screen.query_screen.util.QuerySupplierPurchaseReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.screens.supplier_purchase_screen.report_screen.util.SupplierPurchaseReportScreenEvent
import com.gulfappdeveloper.projectreport.root.CommonMemory
import com.gulfappdeveloper.projectreport.root.HttpRoutes
import com.gulfappdeveloper.projectreport.root.localDateToStringConverter
import com.gulfappdeveloper.projectreport.root.localTimeToStringConverter
import com.gulfappdeveloper.projectreport.root.sendErrorDataToFirebase
import com.gulfappdeveloper.projectreport.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val useCase: UseCase,
    private val commonMemory: CommonMemory,
    private val firebaseService: FirebaseService
) : ViewModel() {

    private val _queryPurchaseMastersReportScreenEvent =
        Channel<QueryPurchaseMastersReportScreenEvent>()
    val queryPurchaseMastersReportScreenEvent =
        _queryPurchaseMastersReportScreenEvent.receiveAsFlow()

    private fun sendQueryPurchaseMastersReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _queryPurchaseMastersReportScreenEvent.send(
                QueryPurchaseMastersReportScreenEvent(
                    uiEvent
                )
            )
        }
    }

    private val _purchaseMastersReportScreenEvent =
        Channel<PurchaseMastersReportScreenEvent>()
    val purchaseMastersReportScreenEvent =
        _purchaseMastersReportScreenEvent.receiveAsFlow()

    private fun sendPurchaseMastersReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _purchaseMastersReportScreenEvent.send(
                PurchaseMastersReportScreenEvent(
                    uiEvent
                )
            )
        }
    }

    private val _querySupplierPurchaseReportScreenEvent =
        Channel<QuerySupplierPurchaseReportScreenEvent>()
    val querySupplierPurchaseReportScreenEvent =
        _querySupplierPurchaseReportScreenEvent.receiveAsFlow()

    private fun sendQuerySupplierPurchaseReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _querySupplierPurchaseReportScreenEvent.send(
                QuerySupplierPurchaseReportScreenEvent(
                    uiEvent
                )
            )
        }
    }

    private val _supplierPurchaseReportScreenEvent =
        Channel<SupplierPurchaseReportScreenEvent>()
    val supplierPurchaseReportScreenEvent =
        _supplierPurchaseReportScreenEvent.receiveAsFlow()

    private fun sendSupplierPurchaseReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _supplierPurchaseReportScreenEvent.send(
                SupplierPurchaseReportScreenEvent(
                    uiEvent
                )
            )
        }
    }

    private val _querySupplierLedgerReportScreenEvent =
        Channel<QuerySupplierLedgerReportScreenEvent>()
    val querySupplierLedgerReportScreenEvent =
        _querySupplierLedgerReportScreenEvent.receiveAsFlow()

    private fun sendQuerySupplierLedgerReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _querySupplierLedgerReportScreenEvent.send(
                QuerySupplierLedgerReportScreenEvent(
                    uiEvent
                )
            )
        }
    }

    private val _supplierLedgerReportScreenEvent =
        Channel<SupplierLedgerReportScreenEvent>()
    val supplierLedgerReportScreenEvent =
        _supplierLedgerReportScreenEvent.receiveAsFlow()

    private fun sendSupplierLedgerReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _supplierLedgerReportScreenEvent.send(
                SupplierLedgerReportScreenEvent(
                    uiEvent
                )
            )
        }
    }

    private val _queryPurchaseSummaryReportScreenEvent =
        Channel<QueryPurchaseSummaryReportScreenUiEvent>()
    val queryPurchaseSummaryReportScreenEvent =
        _queryPurchaseSummaryReportScreenEvent.receiveAsFlow()

    private fun sendQueryPurchaseSummaryReportScreenUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _queryPurchaseSummaryReportScreenEvent.send(
                QueryPurchaseSummaryReportScreenUiEvent(
                    uiEvent
                )
            )
        }
    }

    private val _purchaseSummaryReportScreenEvent =
        Channel<PurchaseSummaryReportScreenUiEvent>()
    val purchaseSummaryReportScreenEvent =
        _purchaseSummaryReportScreenEvent.receiveAsFlow()

    private fun sendPurchaseSummaryReportScreenUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _purchaseSummaryReportScreenEvent.send(
                PurchaseSummaryReportScreenUiEvent(
                    uiEvent
                )
            )
        }
    }


    private val _fromDateState = mutableStateOf("")
    val fromDateState: State<String> = _fromDateState

    private val _fromTimeState = mutableStateOf("")
    val fromTimeState: State<String> = _fromTimeState

    private val _toDateState = mutableStateOf("")
    val toDateState: State<String> = _toDateState

    private val _toTimeState = mutableStateOf("")
    val toTimeState: State<String> = _toTimeState

    private val _partyName = mutableStateOf("")
    val partyName: State<String> = _partyName

    private val _balance = mutableFloatStateOf(0f)
    val balance: State<Float> = _balance

    private val _orientation = mutableStateOf(true)
    val orientation: State<Boolean> = _orientation

    private val _selectedAccount: MutableState<GetCustomerForLedgerReportResponse?> =
        mutableStateOf(null)
    val selectedAccount: State<GetCustomerForLedgerReportResponse?> = _selectedAccount

    fun setSelectedAccount(value: GetCustomerForLedgerReportResponse) {
        _selectedAccount.value = value
    }

    val purchaseMastersReportList = mutableStateListOf<PurchaseMastersResponse>()
    private val _purchaseMastersReportTotal: MutableState<PurchaseMasterTotals?> =
        mutableStateOf(null)
    val purchaseMastersReportTotal: State<PurchaseMasterTotals?> = _purchaseMastersReportTotal

    val purchaseSummaryReportList = mutableStateListOf<PurchaseSummaryResponse>()
    private val _purchaseSummaryReportTotal: MutableState<PurchaseSummaryTotals?> =
        mutableStateOf(null)
    val purchaseSummaryReportTotal: State<PurchaseSummaryTotals?> = _purchaseSummaryReportTotal


    val supplierPurchaseReportList = mutableStateListOf<PurchaseMastersResponse>()
    private val _supplierPurchaseReportTotal: MutableState<PurchaseMasterTotals?> =
        mutableStateOf(null)
    val supplierPurchaseReportTotals: State<PurchaseMasterTotals?> = _supplierPurchaseReportTotal

    val accountList = mutableStateListOf<GetCustomerForLedgerReportResponse>()
    val reArrangedSupplierLedgerReportList = mutableStateListOf<ReArrangedSupplierLedgerDetail>()
    private val _supplierLedgerReportTotals: MutableState<SupplierLedgerTotals?> =
        mutableStateOf(null)
    val supplierLedgerReportTotals: State<SupplierLedgerTotals?> = _supplierLedgerReportTotals


    fun getPurchaseMastersReport(
        fromDate: LocalDate,
        fromTime: LocalTime,
        toDate: LocalDate,
        toTime: LocalTime
    ) {
        val funcName = "RootViewModel." + object {}.javaClass.enclosingMethod?.name + Date()

        val falseDateSelection = checkForInvalidDateSelection(fromDate, fromTime, toDate, toTime)

        if (falseDateSelection) {
            sendQueryPurchaseMastersReportScreenEvent(UiEvent.ShowSnackBar(PresentationConstants.FALSE_DATE_SELECTION))
            return
        }

        _fromDateState.value = fromDate.localDateToStringConverter()
        _fromTimeState.value = fromTime.localTimeToStringConverter()

        _toDateState.value = toDate.localDateToStringConverter()
        _toTimeState.value = toTime.localTimeToStringConverter()

        val fromTimeString = "${fromTime.hour}:${fromTime.minute}:00"
        val toTimeString = "${toTime.hour}:${toTime.minute}:00"


        val fromDateString =
            "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T$fromTimeString"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T$toTimeString"

        val url =
            HttpRoutes.BASE_URL + HttpRoutes.PURCHASE_MASTERS_REPORT + fromDateString + "/$toDateString" + "/${commonMemory.companyId}"
        purchaseMastersReportList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            sendQueryPurchaseMastersReportScreenEvent(UiEvent.ShowProgressBar)
            useCase.purchaseMastersReportUseCase(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendQueryPurchaseMastersReportScreenEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {
                        sendQueryPurchaseMastersReportScreenEvent(UiEvent.CloseProgressBar)
                        val result = value.data
                        purchaseMastersReportList.addAll(result)
                        calculatePurchaseMastersReportTotals(value.data)
                        sendQueryPurchaseMastersReportScreenEvent(UiEvent.Navigate(PurchaseScreens.PurchaseMastersReportScreen.route))

                    }

                    is GetDataFromRemote.Failed -> {
                        val error = value.error
                        firebaseService.sendErrorDataToFirebase(
                            url = url,
                            error = error,
                            funcName = funcName
                        )
                        sendQueryPurchaseMastersReportScreenEvent(UiEvent.CloseProgressBar)
                        sendQueryPurchaseMastersReportScreenEvent(
                            UiEvent.ShowSnackBar(
                                value.error.message ?: "There have some error"
                            )
                        )

                    }

                    else -> {}
                }
            }
        }
    }

    private fun calculatePurchaseMastersReportTotals(data: List<PurchaseMastersResponse>) {
        viewModelScope.launch(Dispatchers.IO) {
            var sumOfTaxable = 0.0
            var sumOfTax = 0.0
            var sumOfNet = 0.0
            var sumOfPayment = 0.0
            var sumOfReturnAmt = 0.0
            var sumOfBalanceAmt = 0.0
            data.forEach {
                sumOfTaxable += it.taxable
                sumOfTax += it.tax
                sumOfNet += it.net
                sumOfPayment += it.payment
                sumOfReturnAmt += it.returnAmount
                sumOfBalanceAmt += it.balanceAmount
            }
            _purchaseMastersReportTotal.value =
                PurchaseMasterTotals(
                    sumOfTaxable = sumOfTaxable,
                    sumOfTax = sumOfTax,
                    sumOfNet = sumOfNet,
                    sumOfPayment = sumOfPayment,
                    sumOfReturnAmount = sumOfReturnAmt,
                    sumOfBalanceAmount = sumOfBalanceAmt
                )
        }

    }

    fun getPurchaseSummaryReport(
        fromDate: LocalDate,
        fromTime: LocalTime,
        toDate: LocalDate,
        toTime: LocalTime
    ) {
        val funcName = "RootViewModel." + object {}.javaClass.enclosingMethod?.name + Date()

        purchaseMastersReportList.clear()
        val falseDateSelection = checkForInvalidDateSelection(fromDate, fromTime, toDate, toTime)

        if (falseDateSelection) {
            sendQueryPurchaseSummaryReportScreenUiEvent(UiEvent.ShowSnackBar(PresentationConstants.FALSE_DATE_SELECTION))
            return
        }

        _fromDateState.value = fromDate.localDateToStringConverter()
        _fromTimeState.value = fromTime.localTimeToStringConverter()

        _toDateState.value = toDate.localDateToStringConverter()
        _toTimeState.value = toTime.localTimeToStringConverter()

        val fromTimeString = "${fromTime.hour}:${fromTime.minute}:00"
        val toTimeString = "${toTime.hour}:${toTime.minute}:00"


        /*  val fromDateString =
              "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}"

          val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}"*/

        val fromDateString =
            "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T$fromTimeString"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T$toTimeString"


        val url =
            HttpRoutes.BASE_URL + HttpRoutes.PURCHASE_SUMMARY_REPORT

        viewModelScope.launch(Dispatchers.IO) {
            useCase.purchaseSummaryReportUseCase(
                url, dateFrom = fromDateString,
                dateTo = toDateString,
                companyId = commonMemory.companyId.toInt()
            ).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendQueryPurchaseSummaryReportScreenUiEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {
                        sendQueryPurchaseSummaryReportScreenUiEvent(UiEvent.CloseProgressBar)
                        purchaseSummaryReportList.addAll(value.data)
                        calculatePurchaseSummaryReportTotal(value.data)
                        sendQueryPurchaseSummaryReportScreenUiEvent(UiEvent.Navigate(route = PurchaseScreens.PurchaseSummaryReportScreen.route))
                    }

                    is GetDataFromRemote.Failed -> {
                        val error = value.error
                        firebaseService.sendErrorDataToFirebase(
                            url = url,
                            error = error,
                            funcName = funcName
                        )
                        sendQueryPurchaseSummaryReportScreenUiEvent(UiEvent.CloseProgressBar)
                        sendQueryPurchaseMastersReportScreenEvent(
                            UiEvent.ShowSnackBar(
                                value.error.message ?: "There have some error"
                            )
                        )
                    }

                    else -> {}
                }

            }
        }
    }

    private fun calculatePurchaseSummaryReportTotal(data: List<PurchaseSummaryResponse>) {
        viewModelScope.launch(Dispatchers.IO) {
            var sumOfTaxable = 0.0
            var sumOfTax = 0.0
            var sumOfNet = 0.0
            data.forEach {
                sumOfTaxable += it.taxable
                sumOfTax += it.tax
                sumOfNet += it.net

            }
            _purchaseSummaryReportTotal.value = PurchaseSummaryTotals(
                sumOfTaxable = sumOfTaxable,
                sumOfTax = sumOfTax,
                sumOfNet = sumOfNet
            )
        }
    }


    fun getSupplierPurchaseReport(
        fromDate: LocalDate,
        fromTime: LocalTime,
        toDate: LocalDate,
        toTime: LocalTime
    ) {
        val funcName = "RootViewModel." + object {}.javaClass.enclosingMethod?.name + Date()

        val falseDateSelection = checkForInvalidDateSelection(fromDate, fromTime, toDate, toTime)

        if (falseDateSelection) {
            sendQuerySupplierPurchaseReportScreenEvent(UiEvent.ShowSnackBar(PresentationConstants.FALSE_DATE_SELECTION))
            return
        }

        if (_selectedAccount.value == null) {
            sendQuerySupplierPurchaseReportScreenEvent(UiEvent.ShowSnackBar("Account is not selected"))
            return
        }
        _fromDateState.value = fromDate.localDateToStringConverter()
        _fromTimeState.value = fromTime.localTimeToStringConverter()

        _toDateState.value = toDate.localDateToStringConverter()
        _toTimeState.value = toTime.localTimeToStringConverter()

        val fromTimeString = "${fromTime.hour}:${fromTime.minute}:00"
        val toTimeString = "${toTime.hour}:${toTime.minute}:00"

        val fromDateString =
            "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T$fromTimeString"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T$toTimeString"
        val url =
            HttpRoutes.BASE_URL + HttpRoutes.SUPPLIER_PURCHASE_REPORT + fromDateString + "/$toDateString" + "/${commonMemory.companyId}" + "/${_selectedAccount.value?.accountId}"
        supplierPurchaseReportList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            useCase.purchaseMastersReportUseCase(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendQuerySupplierPurchaseReportScreenEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {
                        sendQuerySupplierPurchaseReportScreenEvent(UiEvent.CloseProgressBar)
                        val result = value.data
                        supplierPurchaseReportList.addAll(result)
                        calculateSupplierPurchaseReportTotals(result)
                        sendQuerySupplierPurchaseReportScreenEvent(UiEvent.Navigate(PurchaseScreens.SupplierPurchaseReportScreen.route))
                    }

                    is GetDataFromRemote.Failed -> {
                        val error = value.error
                        firebaseService.sendErrorDataToFirebase(
                            url = url,
                            error = error,
                            funcName = funcName
                        )
                        sendQueryPurchaseMastersReportScreenEvent(UiEvent.CloseProgressBar)
                        sendQueryPurchaseMastersReportScreenEvent(
                            UiEvent.ShowSnackBar(
                                value.error.message ?: "There have some error"
                            )
                        )

                    }

                    else -> {}
                }
            }
        }
    }


    private fun calculateSupplierPurchaseReportTotals(data: List<PurchaseMastersResponse>) {


        viewModelScope.launch(Dispatchers.IO) {
            var sumOfTaxable = 0.0
            var sumOfTax = 0.0
            var sumOfNet = 0.0
            var sumOfPayment = 0.0
            var sumOfReturnAmt = 0.0
            var sumOfBalanceAmt = 0.0
            data.forEach {
                sumOfTaxable += it.taxable
                sumOfTax += it.tax
                sumOfNet += it.net
                sumOfPayment += it.payment
                sumOfReturnAmt += it.returnAmount
                sumOfBalanceAmt += it.balanceAmount
            }
            _supplierPurchaseReportTotal.value =
                PurchaseMasterTotals(
                    sumOfTaxable = sumOfTaxable,
                    sumOfTax = sumOfTax,
                    sumOfNet = sumOfNet,
                    sumOfPayment = sumOfPayment,
                    sumOfReturnAmount = sumOfReturnAmt,
                    sumOfBalanceAmount = sumOfBalanceAmt
                )
        }


    }

    fun getSupplierAccountList() {
        val funcName = "RootViewModel." + object {}.javaClass.enclosingMethod?.name + Date()


        val url =
            HttpRoutes.BASE_URL + HttpRoutes.GET_CUSTOMER_FOR_LEDGER + commonMemory.companyId + "/Supplier"

        try {
            accountList.clear()

            viewModelScope.launch(Dispatchers.IO) {
                useCase.getCustomerForLedgerUseCase(url = url).collectLatest { value ->
                    when (value) {
                        is GetDataFromRemote.Loading -> {
                            sendQuerySupplierPurchaseReportScreenEvent(UiEvent.ShowProgressBar)
                            sendQuerySupplierLedgerReportScreenEvent(UiEvent.ShowProgressBar)
                        }

                        is GetDataFromRemote.Success -> {
                            sendQuerySupplierPurchaseReportScreenEvent(UiEvent.CloseProgressBar)
                            sendQuerySupplierLedgerReportScreenEvent(UiEvent.CloseProgressBar)
                            accountList.addAll(value.data)
                            if (accountList.size > 0) {
                                setSelectedAccount(accountList[0])
                            } else {
                                sendQuerySupplierLedgerReportScreenEvent(UiEvent.ShowSnackBar("There have some problemm while fetching account list"))
                                sendQuerySupplierPurchaseReportScreenEvent(UiEvent.ShowSnackBar("There have some problemm while fetching account list"))
                                firebaseService.sendErrorDataToFirebase(
                                    url = url,
                                    error = Error(
                                        702,
                                        message = "Account list is not more than 0"
                                    ),
                                    funcName = funcName
                                )
                            }
                        }

                        is GetDataFromRemote.Failed -> {
                            val error = value.error
                            firebaseService.sendErrorDataToFirebase(
                                url = url,
                                error = error,
                                funcName = funcName
                            )
                            sendQuerySupplierPurchaseReportScreenEvent(UiEvent.CloseProgressBar)
                            sendQuerySupplierLedgerReportScreenEvent(UiEvent.CloseProgressBar)
                            sendQuerySupplierPurchaseReportScreenEvent(
                                UiEvent.ShowSnackBar(
                                    value.error.message ?: "There have some error"
                                )
                            )
                            sendQuerySupplierLedgerReportScreenEvent(
                                UiEvent.ShowSnackBar(
                                    value.error.message ?: "There have some error"
                                )
                            )
                            accountList.clear()
                        }


                        else -> {}
                    }
                }
            }
        } catch (e: IndexOutOfBoundsException) {
            viewModelScope.launch {
                firebaseService.sendErrorDataToFirebase(
                    url = url,
                    error = Error(
                        code = 700,
                        message = e.message
                    ),
                    funcName = funcName
                )
            }

        } catch (e: Exception) {
            viewModelScope.launch {
                firebaseService.sendErrorDataToFirebase(
                    url = url,
                    error = Error(
                        code = 701,
                        message = e.message
                    ),
                    funcName = funcName
                )
            }
        }
    }

    fun getSupplierLedgerReport(
        fromDate: LocalDate,
        fromTime: LocalTime,
        toDate: LocalDate,
        toTime: LocalTime
    ) {
        val funcName = "RootViewModel." + object {}.javaClass.enclosingMethod?.name + Date()

        val falseDateSelection = checkForInvalidDateSelection(fromDate, fromTime, toDate, toTime)

        if (falseDateSelection) {
            sendQuerySupplierLedgerReportScreenEvent(UiEvent.ShowSnackBar(PresentationConstants.FALSE_DATE_SELECTION))
            return
        }

        reArrangedSupplierLedgerReportList.clear()
        _fromDateState.value = fromDate.localDateToStringConverter()
        _fromTimeState.value = fromTime.localTimeToStringConverter()

        _toDateState.value = toDate.localDateToStringConverter()
        _toTimeState.value = toTime.localTimeToStringConverter()

        val fromTimeString = "${fromTime.hour}:${fromTime.minute}:00"
        val toTimeString = "${toTime.hour}:${toTime.minute}:00"

        val fromDateString =
            "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T$fromTimeString"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T$toTimeString"

        val url =
            HttpRoutes.BASE_URL + HttpRoutes.SUPPLIER_LEDGER_REPORT + fromDateString + "/$toDateString" + "/${_selectedAccount.value?.accountId}" + "/${commonMemory.companyId}"
        viewModelScope.launch(Dispatchers.IO) {
            useCase.supplierLedgerReportUseCase(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendQuerySupplierLedgerReportScreenEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {
                        _partyName.value = value.data.partyName
                        _balance.floatValue = value.data.balance
                        calculateSupplierLedgerTotalsAndReArrange(value.data.details)

                    }

                    is GetDataFromRemote.Failed -> {
                        val error = value.error
                        firebaseService.sendErrorDataToFirebase(
                            url = url,
                            error = error,
                            funcName = funcName
                        )
                        sendQuerySupplierLedgerReportScreenEvent(UiEvent.CloseProgressBar)
                        sendQuerySupplierLedgerReportScreenEvent(
                            UiEvent.ShowSnackBar(
                                value.error.message ?: "There have some error"
                            )
                        )

                    }

                    else -> {}
                }
            }
        }
    }

    private fun calculateSupplierLedgerTotalsAndReArrange(data: List<Detail>) {

        viewModelScope.launch(Dispatchers.IO) {
            var sumOfDebit = 0.0
            var sumOfCredit = 0.0
            data.forEachIndexed { index, detail ->
                val reArrangedSupplierLedgerDetail =
                    ReArrangedSupplierLedgerDetail(
                        si = index + 1,
                        voucherDate = detail.vchrDate,
                        voucherNo = detail.vchrNo,
                        particulars = detail.particulars,
                        debit = if (detail.vchrType == "Debit") detail.amount else 0f,
                        credit = if (detail.vchrType == "Credit") detail.amount else 0f

                    )
                reArrangedSupplierLedgerReportList.add(reArrangedSupplierLedgerDetail)
                if (detail.vchrType == "Debit") {
                    sumOfDebit += detail.amount
                } else if (detail.vchrType == "Credit") {
                    sumOfCredit += detail.amount
                }
            }
            _supplierLedgerReportTotals.value =
                SupplierLedgerTotals(sumOfDebit = sumOfDebit, sumOfCredit = sumOfCredit)
            sendQuerySupplierLedgerReportScreenEvent(UiEvent.CloseProgressBar)
            sendQuerySupplierLedgerReportScreenEvent(
                UiEvent.Navigate(
                    PurchaseScreens.SupplierLedgerReportScreen.route
                )
            )

        }

    }

    fun makePdfForSupplierLedgerReport(getUri: (uri: Uri) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            if (reArrangedSupplierLedgerReportList.size > 0) {
                sendSupplierLedgerReportScreenEvent(UiEvent.ShowProgressBar)
                useCase.pdfMakerSupplierLedgerReportUseCase(
                    list = reArrangedSupplierLedgerReportList,
                    _supplierLedgerReportTotals.value!!,
                    balance = _balance.floatValue,
                    partyName = _partyName.value,
                    fromDate = fromDateState.value,
                    fromTime = _fromTimeState.value,
                    toDate = toDateState.value,
                    toTime = _toTimeState.value,
                    getUri = getUri,
                    haveAnyError = { haveAnyError, error ->
                        sendSupplierLedgerReportScreenEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError) {
                            sendSupplierLedgerReportScreenEvent(
                                UiEvent.ShowSnackBar(
                                    error ?: "There have some problem"
                                )
                            )
                        }

                    }

                )
            } else {
                sendSupplierLedgerReportScreenEvent(UiEvent.ShowSnackBar("List is empty"))
            }
        }
    }

    fun makeExcelForSupplierLedgerReport(getUri: (uri: Uri) -> Unit) {
        if (reArrangedSupplierLedgerReportList.size > 0) {
            viewModelScope.launch(Dispatchers.IO) {
                sendSupplierLedgerReportScreenEvent(UiEvent.ShowProgressBar)
                useCase.excelMakerForSupplierLedgerReportUseCase(
                    list = reArrangedSupplierLedgerReportList,
                    balance = _balance.floatValue,
                    partyName = _partyName.value,
                    fromDate = fromDateState.value,
                    fromTime = _fromTimeState.value,
                    toDate = toDateState.value,
                    toTime = _toTimeState.value,
                    getUri = getUri,
                    haveAnyError = { haveAnyError, error ->
                        sendSupplierLedgerReportScreenEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError) {
                            sendSupplierLedgerReportScreenEvent(
                                UiEvent.ShowSnackBar(
                                    error ?: "There have some problem"
                                )
                            )
                        }

                    }

                )
            }
        } else {
            sendSupplierLedgerReportScreenEvent(UiEvent.ShowSnackBar("List is empty"))
        }
    }


    fun setOrientation(orientation: Int) {
        if (orientation == 1) {
            _orientation.value = true
        }
        if (orientation == 2) {
            _orientation.value = false
        }
    }

    fun makePdfForPurchaseMastersReport(getUri: (uri: Uri) -> Unit) {
        if (purchaseMastersReportList.size > 0) {
            sendPurchaseMastersReportScreenEvent(UiEvent.ShowProgressBar)
            viewModelScope.launch(Dispatchers.IO) {
                useCase.pdfMakerPurchaseMastersReportUseCase(
                    fromDate = fromDateState.value,
                    fromTime = _fromTimeState.value,
                    toDate = toDateState.value,
                    toTime = _toTimeState.value,
                    getUri = getUri,
                    list = purchaseMastersReportList,
                    purchaseMastersReportListTotals = _purchaseMastersReportTotal.value!!,
                    purchaseMasterSelection = PurchaseMasterSelection.PURCHASE_MASTER,
                    haveAnyError = { haveAnyError, error ->
                        sendPurchaseMastersReportScreenEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError) {
                            sendPurchaseMastersReportScreenEvent(
                                UiEvent.ShowSnackBar(
                                    error ?: "There have some problem"
                                )
                            )
                        }
                    }
                )
            }

        } else {
            sendPurchaseMastersReportScreenEvent(UiEvent.ShowSnackBar("List is empty"))
        }

    }

    fun makePdfForPurchaseSummaryReport(getUri: (uri: Uri) -> Unit) {
        if (purchaseSummaryReportList.size > 0) {
            sendPurchaseSummaryReportScreenUiEvent(UiEvent.ShowProgressBar)
            viewModelScope.launch(Dispatchers.IO) {
                useCase.purchaseSummaryReportPdfUseCase(
                    fromDate = fromDateState.value,
                    fromTime = _fromTimeState.value,
                    toDate = toDateState.value,
                    toTime = _toTimeState.value,
                    getUri = getUri,
                    list = purchaseSummaryReportList,
                    purchaseSummaryTotals = _purchaseSummaryReportTotal.value!!,
                    haveAnyError = { haveAnyError, error ->
                        sendPurchaseSummaryReportScreenUiEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError) {
                            sendPurchaseSummaryReportScreenUiEvent(
                                UiEvent.ShowSnackBar(
                                    error ?: "There have some problem"
                                )
                            )
                        }
                    }
                )
            }

        } else {
            sendPurchaseSummaryReportScreenUiEvent(UiEvent.ShowSnackBar("List is empty"))
        }

    }

    fun makeExcelForPurchaseSummaryReport(getUri: (uri: Uri) -> Unit) {
        if (purchaseSummaryReportList.size > 0) {
            sendPurchaseSummaryReportScreenUiEvent(UiEvent.ShowProgressBar)
            viewModelScope.launch(Dispatchers.IO) {
                useCase.purchaseSummaryReportExcelUseCase(
                    fromDate = fromDateState.value,
                    fromTime = _fromTimeState.value,
                    toDate = toDateState.value,
                    toTime = _toTimeState.value,
                    getUri = getUri,
                    list = purchaseSummaryReportList,
                    haveAnyError = { haveAnyError, error ->
                        sendPurchaseSummaryReportScreenUiEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError) {
                            sendPurchaseSummaryReportScreenUiEvent(
                                UiEvent.ShowSnackBar(
                                    error ?: "There have some problem"
                                )
                            )
                        }
                    }
                )
            }

        } else {
            sendPurchaseSummaryReportScreenUiEvent(UiEvent.ShowSnackBar("List is empty"))
        }
    }

    fun makeExcelForPurchaseMasterReport(getUri: (uri: Uri) -> Unit) {
        if (purchaseMastersReportList.size > 0) {
            sendPurchaseMastersReportScreenEvent(UiEvent.ShowProgressBar)
            viewModelScope.launch(Dispatchers.IO) {
                useCase.excelMakerPurchaseMastersReportUseCase(
                    fromDate = fromDateState.value,
                    fromTime = _fromTimeState.value,
                    toDate = toDateState.value,
                    toTime = _toTimeState.value,
                    getUri = getUri,
                    purchaseMasterSelection = PurchaseMasterSelection.PURCHASE_MASTER,
                    list = purchaseMastersReportList,
                    haveAnyError = { haveAnyError, error ->
                        sendPurchaseMastersReportScreenEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError) {
                            sendPurchaseMastersReportScreenEvent(
                                UiEvent.ShowSnackBar(
                                    error ?: "There have some problem"
                                )
                            )
                        }
                    }
                )
            }

        } else {
            sendPurchaseMastersReportScreenEvent(UiEvent.ShowSnackBar("List is empty"))
        }
    }

    fun makePdfForSupplierPurchaseReport(getUri: (uri: Uri) -> Unit) {
        if (supplierPurchaseReportList.size > 0) {
            sendSupplierPurchaseReportScreenEvent(UiEvent.ShowProgressBar)
            viewModelScope.launch(Dispatchers.IO) {
                useCase.pdfMakerPurchaseMastersReportUseCase(
                    fromDate = _fromDateState.value,
                    fromTime = _fromDateState.value,
                    toDate = _toDateState.value,
                    toTime = _toTimeState.value,
                    getUri = getUri,
                    list = supplierPurchaseReportList,
                    purchaseMastersReportListTotals = _supplierPurchaseReportTotal.value!!,
                    purchaseMasterSelection = PurchaseMasterSelection.SUPPLIER_PURCHASE,
                    haveAnyError = { haveAnyError, error ->
                        sendSupplierPurchaseReportScreenEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError) {
                            sendSupplierPurchaseReportScreenEvent(
                                UiEvent.ShowSnackBar(
                                    error ?: "There have some problem"
                                )
                            )
                        }
                    }
                )
            }

        } else {
            sendSupplierPurchaseReportScreenEvent(UiEvent.ShowSnackBar("List is empty"))
        }
    }

    fun makeExcelForSupplierPurchaseReport(getUri: (uri: Uri) -> Unit) {
        if (supplierPurchaseReportList.size > 0) {
            sendSupplierPurchaseReportScreenEvent(UiEvent.ShowProgressBar)
            viewModelScope.launch(Dispatchers.IO) {
                useCase.excelMakerPurchaseMastersReportUseCase(
                    fromDate = _fromDateState.value,
                    fromTime = _fromTimeState.value,
                    toDate = _toDateState.value,
                    toTime = _toTimeState.value,
                    getUri = getUri,
                    list = supplierPurchaseReportList,
                    purchaseMasterSelection = PurchaseMasterSelection.SUPPLIER_PURCHASE,
                    haveAnyError = { haveAnyError, error ->
                        sendSupplierPurchaseReportScreenEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError) {
                            sendSupplierPurchaseReportScreenEvent(
                                UiEvent.ShowSnackBar(
                                    error ?: "There have some problem"
                                )
                            )
                        }
                    }
                )
            }

        } else {
            sendSupplierPurchaseReportScreenEvent(UiEvent.ShowSnackBar("List is empty"))
        }
    }


    private fun checkForInvalidDateSelection(
        fromDate: LocalDate,
        fromTime: LocalTime,
        toDate: LocalDate,
        toTime: LocalTime
    ): Boolean {

        return if (toDate.isBefore(fromDate)) {
            true
        } else if (toDate.isEqual(fromDate)) {
            toTime.minusSeconds(1L).isBefore(fromTime)
        } else {
            false
        }
    }


}