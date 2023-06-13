package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.ledger.GetCustomerForLedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.ledger.LedgerDetail
import com.gulfappdeveloper.projectreport.domain.models.sales.SaleSummariesResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.SalesInvoiceResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.UserSalesResponse
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.navigation.LedgerReportScreens
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.navigation.SalesScreens
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.customer_ledger_screens.query_screen.util.QueryCustomerLedgerReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.customer_payment_report_screens.query_screen.util.QueryCustomerPaymentReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_invoice_report_screens.query_screen.util.QuerySalesInvoiceReportEvent
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sale_summaries_report_screens.query_screen.util.QuerySaleSummariesReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.user_sales_report_screens.query_screen.util.QueryUserSalesReportScreenEvent
import com.gulfappdeveloper.projectreport.root.CommonMemory
import com.gulfappdeveloper.projectreport.root.HttpRoutes
import com.gulfappdeveloper.projectreport.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

private const val TAG = "SalesViewModel"

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val useCase: UseCase,
    private val commonMemory: CommonMemory,
) : ViewModel() {

    private val _querySalesInvoiceReportScreenEvent = Channel<QuerySalesInvoiceReportEvent>()
    val querySalesInvoiceReportEvent = _querySalesInvoiceReportScreenEvent.receiveAsFlow()

    private fun sendQuerySalesInvoiceReportEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _querySalesInvoiceReportScreenEvent.send(QuerySalesInvoiceReportEvent(uiEvent))
        }
    }


    private val _querySaleSummariesReportScreenEvent =
        Channel<QuerySaleSummariesReportScreenEvent>()
    val querySaleSummariesReportScreenEvent = _querySaleSummariesReportScreenEvent.receiveAsFlow()

    private fun sendQuerySaleSummariesReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _querySaleSummariesReportScreenEvent.send(QuerySaleSummariesReportScreenEvent(uiEvent))
        }
    }

    private val _queryUserSalesReportScreenEvent =
        Channel<QueryUserSalesReportScreenEvent>()
    val queryUserSalesReportScreenEvent = _queryUserSalesReportScreenEvent.receiveAsFlow()

    private fun sendQueryUserSalesReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _queryUserSalesReportScreenEvent.send(QueryUserSalesReportScreenEvent(uiEvent))
        }
    }

    private val _queryCustomerPaymentReportScreenEvent =
        Channel<QueryCustomerPaymentReportScreenEvent>()
    val queryCustomerPaymentReportScreenEvent =
        _queryCustomerPaymentReportScreenEvent.receiveAsFlow()

    private fun sendQueryCustomerPaymentReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _queryCustomerPaymentReportScreenEvent.send(
                QueryCustomerPaymentReportScreenEvent(
                    uiEvent
                )
            )
        }
    }

    private val _queryCustomerLedgerReportScreenEvent =
        Channel<QueryCustomerLedgerReportScreenEvent>()
    val queryCustomerLedgerReportScreenEvent = _queryCustomerLedgerReportScreenEvent.receiveAsFlow()

    private fun sendQueryCustomerLedgerReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _queryCustomerLedgerReportScreenEvent.send(QueryCustomerLedgerReportScreenEvent(uiEvent))
        }
    }

    private val _fromDateState = mutableStateOf("")
    val fromDateState: State<String> = _fromDateState

    private val _toDateState = mutableStateOf("")
    val toDateState: State<String> = _toDateState

    private val _partyName = mutableStateOf("")
    val partyName: State<String> = _partyName

    private val _balance = mutableStateOf(0f)
    val balance: State<Float> = _balance

    private val _orientation = mutableStateOf(true)
    val orientation: State<Boolean> = _orientation

    private val _selectedAccount: MutableState<GetCustomerForLedgerReportResponse?> =
        mutableStateOf(null)
    val selectedAccount: State<GetCustomerForLedgerReportResponse?> = _selectedAccount

    fun setSelectedAccount(value: GetCustomerForLedgerReportResponse) {
        _selectedAccount.value = value
    }


    val salesInvoiceReportList = mutableStateListOf<SalesInvoiceResponse>()
    val saleSummariesReportList = mutableStateListOf<SaleSummariesResponse>()
    val userSalesReportList = mutableStateListOf<UserSalesResponse>()

    val customerPaymentReportList = mutableStateListOf<CustomerPaymentResponse>()
    val customerPaymentReportTotalList = mutableStateListOf<Double>()

    val accountList = mutableStateListOf<GetCustomerForLedgerReportResponse>()
    val customerLedgerReportList = mutableStateListOf<LedgerDetail>()

    fun getSalesInvoiceReport(fromDate: LocalDate, toDate: LocalDate) {
        _fromDateState.value = fromDate.toString()
        _toDateState.value = toDate.toString()
        val fromDateString =
            "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T00:00:00"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T00:00:00"
        val url =
            HttpRoutes.BASE_URL + HttpRoutes.SALES_INVOICE_REPORT + fromDateString + "/$toDateString" + "/${commonMemory.companyId}"
        Log.d(TAG, "getSaleInvoiceReport: $url")

        salesInvoiceReportList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getSalesInvoiceReportUseCase(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendQuerySalesInvoiceReportEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {
                        sendQuerySalesInvoiceReportEvent(UiEvent.CloseProgressBar)
                        salesInvoiceReportList.addAll(value.data)
                        Log.d(TAG, "getSalesInvoiceReport: ${value.data}")
                        sendQuerySalesInvoiceReportEvent(UiEvent.Navigate(SalesScreens.SalesInvoiceReportScreen.route))
                    }

                    is GetDataFromRemote.Failed -> {
                        sendQuerySalesInvoiceReportEvent(UiEvent.CloseProgressBar)
                        sendQuerySalesInvoiceReportEvent(
                            UiEvent.ShowSnackBar(
                                value.error.message ?: ""
                            )
                        )
                        Log.e(TAG, "getSalesInvoiceReport: ${value.error}")
                    }
                }

            }
        }
    }


    fun getSaleSummariesReport(fromDate: LocalDate, toDate: LocalDate) {
        _fromDateState.value = fromDate.toString()
        _toDateState.value = toDate.toString()
        val fromDateString =
            "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T00:00:00"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T00:00:00"
        val url =
            HttpRoutes.BASE_URL + HttpRoutes.SALE_SUMMARIES_REPORT + fromDateString + "/$toDateString" + "/${commonMemory.companyId}"
        Log.d(TAG, "getSaleSummariesReport: $url")

        saleSummariesReportList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getSalesSummariesReportUseCase(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendQuerySaleSummariesReportScreenEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {
                        sendQuerySaleSummariesReportScreenEvent(UiEvent.CloseProgressBar)
                        saleSummariesReportList.addAll(value.data)
                        sendQuerySaleSummariesReportScreenEvent(UiEvent.Navigate(route = SalesScreens.SaleSummariesReportScreen.route))
                        Log.e(TAG, "getSaleSummariesReport: ${value.data}")
                    }

                    is GetDataFromRemote.Failed -> {
                        sendQuerySaleSummariesReportScreenEvent(UiEvent.CloseProgressBar)
                        sendQuerySaleSummariesReportScreenEvent(
                            UiEvent.ShowSnackBar(
                                value.error.message ?: ""
                            )
                        )
                        Log.e(TAG, "getSaleSummariesReport: ${value.error}")
                    }
                }

            }
        }
    }

    fun getUserSalesReport(fromDate: LocalDate, toDate: LocalDate) {
        _fromDateState.value = fromDate.toString()
        _toDateState.value = toDate.toString()
        val fromDateString =
            "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T00:00:00"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T00:00:00"
        val url =
            HttpRoutes.BASE_URL + HttpRoutes.USER_SALES + fromDateString + "/$toDateString" + "/${commonMemory.companyId}"
        Log.d(TAG, "getUserSalesReport: $url")

        userSalesReportList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getUserSalesReportUseCase(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendQueryUserSalesReportScreenEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {
                        sendQueryUserSalesReportScreenEvent(UiEvent.CloseProgressBar)
                        userSalesReportList.addAll(value.data)
                        sendQueryUserSalesReportScreenEvent(UiEvent.Navigate(route = SalesScreens.UserSalesReportScreen.route))
                        Log.e(TAG, "getUserSalesReport: ${value.data}")
                    }

                    is GetDataFromRemote.Failed -> {
                        sendQueryUserSalesReportScreenEvent(UiEvent.CloseProgressBar)
                        sendQueryUserSalesReportScreenEvent(
                            UiEvent.ShowSnackBar(
                                value.error.message ?: ""
                            )
                        )
                        Log.e(TAG, "getUserSalesReport: ${value.error}")
                    }
                }

            }
        }
    }


    fun getCustomerPaymentReport(fromDate: LocalDate, toDate: LocalDate) {
        _fromDateState.value = fromDate.toString()
        _toDateState.value = toDate.toString()
        val fromDateString =
            "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T00:00:00"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T00:00:00"
        val url =
            HttpRoutes.BASE_URL + HttpRoutes.GET_CUSTOMER_PAYMENT_REPORT + fromDateString + "/$toDateString" + "/${commonMemory.companyId}"
        Log.d(TAG, "getCustomerPaymentReport: $url")
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getCustomerPaymentUseCase(url = url).collectLatest { value ->
                customerPaymentReportList.clear()
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendQueryCustomerPaymentReportScreenEvent(UiEvent.ShowProgressBar)

                    }

                    is GetDataFromRemote.Success -> {
                        //Log.d(TAG, "getCustomerPaymentReport: ${value.data}")
                        sendQueryCustomerPaymentReportScreenEvent(UiEvent.CloseProgressBar)
                        customerPaymentReportList.addAll(value.data)
                        customerPaymentReportTotalList.addAll(calculateTotalForCustomerPaymentReport(value.data))
                        sendQueryCustomerPaymentReportScreenEvent(UiEvent.Navigate(SalesScreens.CustomerPaymentReportScreen.route))


                    }

                    is GetDataFromRemote.Failed -> {
                        sendQueryCustomerPaymentReportScreenEvent(UiEvent.CloseProgressBar)
                        Log.e(TAG, "getCustomerPaymentReport: ${value.error}")
                        sendQueryCustomerPaymentReportScreenEvent(UiEvent.ShowSnackBar(value.error.message!!))
                    }
                }
            }
        }
    }


    fun getCustomerForLedger() {
        val url =
            HttpRoutes.BASE_URL + HttpRoutes.GET_CUSTOMER_FOR_LEDGER + commonMemory.companyId + "/Customer"

        viewModelScope.launch(Dispatchers.IO) {
            useCase.getCustomerForLedgerUseCase(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Success -> {
                        sendQueryCustomerLedgerReportScreenEvent(UiEvent.CloseProgressBar)
                        accountList.clear()
                        accountList.addAll(value.data)
                        setSelectedAccount(value.data[0])
                    }

                    is GetDataFromRemote.Failed -> {
                        sendQueryCustomerLedgerReportScreenEvent(UiEvent.CloseProgressBar)
                        sendQueryCustomerLedgerReportScreenEvent(
                            UiEvent.ShowSnackBar(
                                value.error.message ?: ""
                            )
                        )
                        accountList.clear()
                    }

                    is GetDataFromRemote.Loading -> {
                        sendQueryCustomerLedgerReportScreenEvent(UiEvent.ShowProgressBar)
                    }
                }
            }
        }
    }


    fun getCustomerLedgerReport(fromDate: LocalDate, toDate: LocalDate) {
        if (_selectedAccount.value == null) {
            sendQueryCustomerLedgerReportScreenEvent(UiEvent.ShowSnackBar("No Account is selected"))
            return
        }
        val fromDateString =
            "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T00:00:00"
        _fromDateState.value = "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T00:00:00"
        _toDateState.value = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}"

        val url =
            HttpRoutes.BASE_URL + HttpRoutes.LEDGER_REPORT + fromDateString + "/$toDateString" + "/${_selectedAccount.value?.accountId}" + "/${commonMemory.companyId}"

        viewModelScope.launch(Dispatchers.IO) {
            useCase.getCustomerLedgers(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendQueryCustomerLedgerReportScreenEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {
                        _partyName.value = value.data.partyName
                        _balance.value = value.data.balance
                        customerLedgerReportList.clear()
                        Log.e(TAG, "getCustomerLedgerReport: ${value.data}")
                        customerLedgerReportList.addAll(value.data.details)
                        sendQueryCustomerLedgerReportScreenEvent(UiEvent.CloseProgressBar)
                        sendQueryCustomerLedgerReportScreenEvent(
                            UiEvent.Navigate(
                                SalesScreens.CustomerLedgerReportScreen.route
                            )
                        )
                    }

                    is GetDataFromRemote.Failed -> {
                        sendQueryCustomerLedgerReportScreenEvent(UiEvent.CloseProgressBar)
                        sendQueryCustomerLedgerReportScreenEvent(UiEvent.ShowSnackBar("failed"))
                    }
                }
            }
        }
    }

    fun makePdfForCustomerPaymentReport(getUri: (uri: Uri) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            if (customerPaymentReportList.size > 0) {
                useCase.pdfMakerUseCaseForCustomerPaymentReport(
                    list = customerPaymentReportList,
                    _fromDateState.value,
                    _toDateState.value,
                    getUri = getUri
                ) { error, errorS ->
                    Log.e(TAG, "getCustomerPaymentReport: $error $errorS")
                }
            }
        }
    }

    fun makeExcelForCustomerPaymentReport(getUri: (uri: Uri) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            if (customerPaymentReportList.size > 0) {
                useCase.excelMakerUseCaseForCustomerPaymentReport(
                    list = customerPaymentReportList,
                    _fromDateState.value,
                    _toDateState.value,
                    getUri = getUri
                ) { error, errorS ->
                    Log.e(TAG, "getCustomerPaymentReport: $error $errorS")
                }
            }
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

    private fun calculateTotalForCustomerPaymentReport(list: List<CustomerPaymentResponse>): List<Double> {
        var sumOfCash = 0.0
        var sumOfCard = 0.0
        var sumOfOnline = 0.0
        var sumOfCredit = 0.0
        var sumOfTotal = 0.0
        viewModelScope.launch(Dispatchers.IO) {


            list.forEach {
                sumOfCash += it.cash.toFloat()
                sumOfCard += it.card.toFloat()
                sumOfOnline += it.onlineAmount.toFloat()
                sumOfCredit += it.credit.toFloat()
                sumOfTotal += it.total.toFloat()

            }
        }
        return listOf(
            sumOfCash,
            sumOfCard,
            sumOfOnline,
            sumOfCredit,
            sumOfTotal,
        )

    }


}