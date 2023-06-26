package com.gulfappdeveloper.projectreport.presentation.screens.account_screens

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.projectreport.domain.models.accounts.ExpenseLedgerDetail
import com.gulfappdeveloper.projectreport.domain.models.accounts.PaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.accounts.ReceiptResponse
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.ledger.GetCustomerForLedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.purchase.supplier_ledger_report.Detail
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.account_models.ExpenseLedgerReportTotals
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.account_models.ReArrangedExpenseLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.navigation.AccountScreens
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.expense_ledger_report_screen.query.util.QueryExpenseLedgerScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.expense_ledger_report_screen.report.util.ExpenseLedgerScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.payments_report_screen.query.util.QueryPaymentsReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.payments_report_screen.report.util.PaymentsReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.receipts_report_screen.query.util.QueryReceiptsReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.screens.receipts_report_screen.report.util.ReceiptsReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.ReArrangedSupplierLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.SupplierLedgerTotals
import com.gulfappdeveloper.projectreport.root.CommonMemory
import com.gulfappdeveloper.projectreport.root.HttpRoutes
import com.gulfappdeveloper.projectreport.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.apache.commons.collections4.Get
import java.time.LocalDate
import javax.inject.Inject

private const val TAG = "AccountViewModel"

@HiltViewModel
class AccountViewModel
@Inject constructor(
    private val useCase: UseCase,
    private val commonMemory: CommonMemory
) : ViewModel() {

    private val _queryExpenseLedgerReportScreenEvent =
        Channel<QueryExpenseLedgerScreenEvent>()
    val queryExpenseLedgerReportScreenEvent =
        _queryExpenseLedgerReportScreenEvent.receiveAsFlow()

    val reArrangedExpenseLedgerReportList = mutableStateListOf<ReArrangedExpenseLedgerDetail>()


    private fun sendQueryExpenseLedgerScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _queryExpenseLedgerReportScreenEvent.send(
                QueryExpenseLedgerScreenEvent(
                    uiEvent
                )
            )
        }
    }

    private val _expenseLedgerReportScreenEvent =
        Channel<ExpenseLedgerScreenEvent>()
    val expenseLedgerReportScreenEvent =
        _expenseLedgerReportScreenEvent.receiveAsFlow()

    private fun sendExpenseLedgerScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _expenseLedgerReportScreenEvent.send(
                ExpenseLedgerScreenEvent(
                    uiEvent
                )
            )
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

    private val _expenseLedgerReportTotals: MutableState<ExpenseLedgerReportTotals?> =
        mutableStateOf(null)
    val expenseLedgerReportTotals: State<ExpenseLedgerReportTotals?> = _expenseLedgerReportTotals

    val accountList = mutableStateListOf<GetCustomerForLedgerReportResponse>()


    fun getCustomerAccountList() {
        accountList.clear()
        val url =
            HttpRoutes.BASE_URL + HttpRoutes.GET_CUSTOMER_FOR_LEDGER + commonMemory.companyId + "/Expense"

        viewModelScope.launch(Dispatchers.IO) {
            useCase.getCustomerForLedgerUseCase(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendQueryExpenseLedgerScreenEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {
                        sendQueryExpenseLedgerScreenEvent(UiEvent.CloseProgressBar)
                        accountList.addAll(value.data)
                        setSelectedAccount(value.data[0])
                    }

                    is GetDataFromRemote.Failed -> {
                        sendQueryExpenseLedgerScreenEvent(UiEvent.CloseProgressBar)
                        sendQueryExpenseLedgerScreenEvent(
                            UiEvent.ShowSnackBar(
                                value.error.message ?: "There have some error"
                            )
                        )
                        accountList.clear()
                    }


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

    fun getExpenseLedgerReport(fromDate: LocalDate, toDate: LocalDate) {
        reArrangedExpenseLedgerReportList.clear()
        if (_selectedAccount.value == null) {
            sendQueryExpenseLedgerScreenEvent(UiEvent.ShowSnackBar("No Account is selected"))
            return
        }
        val fromDateString =
            "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T00:00:00"
        _fromDateState.value = "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T00:00:00"
        _toDateState.value = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}"

        val url =
            HttpRoutes.BASE_URL + HttpRoutes.EXPENSE_LEDGER_REPORT
        Log.e(TAG, "getSupplierLedgerReport: $url")
        viewModelScope.launch(Dispatchers.IO) {
            useCase.expenseLedgerReportUseCase(
                url = url,
                dateFrom = fromDateString,
                dateTo = toDateString,
                companyId = commonMemory.companyId.toInt(),
                expenseId = _selectedAccount.value?.accountId!!

            ).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendQueryExpenseLedgerScreenEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {
                        _partyName.value = value.data.partyName
                        _balance.value = value.data.balance
                        sendQueryExpenseLedgerScreenEvent(UiEvent.CloseProgressBar)
                        calculateExpenseLedgerTotalsAndReArrange(data = value.data.details)
                        Log.d(TAG, "getExpenseLedgerReport: ${value.data}")
                        sendQueryExpenseLedgerScreenEvent(UiEvent.Navigate(AccountScreens.ExpenseLedgerScreen.route))
                    }

                    is GetDataFromRemote.Failed -> {
                        Log.e(TAG, "getExpenseLedgerReport: ${value.error}")
                        sendQueryExpenseLedgerScreenEvent(UiEvent.CloseProgressBar)
                    }
                }

            }
        }
    }

    private fun calculateExpenseLedgerTotalsAndReArrange(data: List<ExpenseLedgerDetail>) {

        var sumOfDebit = 0.0
        var sumOfCredit = 0.0

        viewModelScope.launch(Dispatchers.IO) {
            data.forEachIndexed { index, detail ->
                val reArrangedExpenseLedgerDetail =
                    ReArrangedExpenseLedgerDetail(
                        si = index + 1,
                        voucherDate = detail.vchrDate,
                        voucherNo = detail.vchrNo,
                        particulars = detail.particulars,
                        debit = if (detail.vchrType == "Debit") detail.amount.toFloat() else 0f,
                        credit = if (detail.vchrType == "Credit") detail.amount.toFloat() else 0f

                    )
                reArrangedExpenseLedgerReportList.add(reArrangedExpenseLedgerDetail)
                if (detail.vchrType == "Debit") {
                    sumOfDebit += detail.amount
                } else if (detail.vchrType == "Credit") {
                    sumOfCredit += detail.amount
                }
            }
        }


        _expenseLedgerReportTotals.value =
            ExpenseLedgerReportTotals(sumOfDebit = sumOfDebit, sumOfCredit = sumOfCredit)


    }

    fun makePdfForExpenseLedgerReport(getUri: (uri: Uri) -> Unit) {
        if (reArrangedExpenseLedgerReportList.size > 0) {
            sendExpenseLedgerScreenEvent(UiEvent.ShowProgressBar)
            viewModelScope.launch(Dispatchers.IO) {
                useCase.pdfMakerExpenseLedgerReportUseCase(
                    list = reArrangedExpenseLedgerReportList,
                    expenseLedgerTotal = _expenseLedgerReportTotals.value!!,
                    balance = _balance.value,
                    partyName = _partyName.value,
                    getUri = getUri,
                    fromDate = _fromDateState.value,
                    toDate = _toDateState.value,
                    haveAnyError = { haveAnyError, error ->
                        sendExpenseLedgerScreenEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError) {
                            sendExpenseLedgerScreenEvent(
                                UiEvent.ShowSnackBar(
                                    error ?: "There have some error"
                                )
                            )
                        }
                    }

                )
            }
        } else {
            sendExpenseLedgerScreenEvent(UiEvent.ShowSnackBar("List is empty"))
        }
    }

    fun makeExcelForExpenseLedgerReport(getUri: (uri: Uri) -> Unit) {
        if (reArrangedExpenseLedgerReportList.size > 0) {
            sendExpenseLedgerScreenEvent(UiEvent.ShowProgressBar)
            viewModelScope.launch(Dispatchers.IO) {
                useCase.excelMakerExpenseLedgerReportUseCase(
                    list = reArrangedExpenseLedgerReportList,
                    balance = _balance.value,
                    partyName = _partyName.value,
                    getUri = getUri,
                    fromDate = _fromDateState.value,
                    toDate = _toDateState.value,
                    haveAnyError = { haveAnyError, error ->
                        sendExpenseLedgerScreenEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError) {
                            sendExpenseLedgerScreenEvent(
                                UiEvent.ShowSnackBar(
                                    error ?: "There have some error"
                                )
                            )
                        }
                    }

                )
            }
        } else {
            sendExpenseLedgerScreenEvent(UiEvent.ShowSnackBar("List is empty"))
        }
    }

    private val _queryPaymentsReportScreenEvent =
        Channel<QueryPaymentsReportScreenEvent>()
    val queryPaymentsReportScreenEvent =
        _queryPaymentsReportScreenEvent.receiveAsFlow()


    private fun sendQueryPaymentsReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _queryPaymentsReportScreenEvent.send(
                QueryPaymentsReportScreenEvent(
                    uiEvent
                )
            )
        }
    }

    private val _paymentsReportScreenEvent =
        Channel<PaymentsReportScreenEvent>()
    val paymentsReportScreenEvent =
        _paymentsReportScreenEvent.receiveAsFlow()


    private fun sendPaymentsReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _paymentsReportScreenEvent.send(
                PaymentsReportScreenEvent(
                    uiEvent
                )
            )
        }
    }

    private val _queryReceiptsReportScreenEvent =
        Channel<QueryReceiptsReportScreenEvent>()
    val queryReceiptsReportScreenEvent =
        _queryReceiptsReportScreenEvent.receiveAsFlow()


    private fun sendQueryReceiptsReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _queryReceiptsReportScreenEvent.send(
                QueryReceiptsReportScreenEvent(
                    uiEvent
                )
            )
        }
    }

    private val _receiptsReportScreenEvent =
        Channel<ReceiptsReportScreenEvent>()
    val receiptsReportScreenEvent =
        _receiptsReportScreenEvent.receiveAsFlow()


    private fun sendReceiptsReportScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _receiptsReportScreenEvent.send(
                ReceiptsReportScreenEvent(
                    uiEvent
                )
            )
        }
    }

    val paymentsReportList = mutableStateListOf<PaymentResponse>()
    private val _paymentReportListTotal: MutableState<Double> = mutableStateOf(0.0)
    val paymentReportListTotals: State<Double> = _paymentReportListTotal

    val receiptsReportList = mutableStateListOf<ReceiptResponse>()
    private val _receiptReportListTotal: MutableState<Double> = mutableStateOf(0.0)
    val receiptReportListTotal: State<Double> = _receiptReportListTotal


    fun getPaymentsReport(fromDate: LocalDate, toDate: LocalDate) {
        paymentsReportList.clear()
        val fromDateString =
            "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T00:00:00"
        _fromDateState.value = "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T00:00:00"
        _toDateState.value = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}"

        val url =
            HttpRoutes.BASE_URL + HttpRoutes.PAYMENT_REPORT
        Log.e(TAG, "getSupplierLedgerReport: $url")

        viewModelScope.launch(Dispatchers.IO) {
            useCase.getPaymentsReportUseCase(
                url = url,
                dateFrom = fromDateString,
                dateTo = toDateString,
                companyId = commonMemory.companyId.toInt()
            ).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendQueryPaymentsReportScreenEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {
                        sendQueryPaymentsReportScreenEvent(UiEvent.CloseProgressBar)
                        paymentsReportList.addAll(value.data)
                        calculatePaymentReportTotals(value.data)
                        Log.e(TAG, "getPaymentsReport: ${value.data}", )
                        sendQueryPaymentsReportScreenEvent(UiEvent.Navigate(AccountScreens.PaymentsReportScreen.route))
                    }

                    is GetDataFromRemote.Failed -> {
                        sendQueryPaymentsReportScreenEvent(UiEvent.CloseProgressBar)
                        sendQueryPaymentsReportScreenEvent(
                            UiEvent.ShowSnackBar(
                                value.error.message ?: "There have some error"
                            )
                        )

                    }
                }
            }
        }
    }

    private fun calculatePaymentReportTotals(data: List<PaymentResponse>) {
        var sumOfAmount = 0.0
        data.forEach {
            sumOfAmount += it.amount
        }
        _paymentReportListTotal.value = sumOfAmount
    }

    fun makePdfForPaymentReport(getUri: (uri: Uri) -> Unit){
        if (paymentsReportList.size>0){
            sendPaymentsReportScreenEvent(UiEvent.ShowProgressBar)
            viewModelScope.launch (Dispatchers.IO){
                useCase.makePdfForPaymentsReportUseCase(
                    fromDate = _fromDateState.value,
                    toDate = _toDateState.value,
                    list = paymentsReportList,
                    paymentReportListTotal = _paymentReportListTotal.value,
                    getUri = getUri,
                    haveAnyError = {haveAnyError, error ->
                        sendPaymentsReportScreenEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError){
                            sendPaymentsReportScreenEvent(UiEvent.ShowSnackBar(error ?:"There have some problem"))
                        }

                    }
                )
            }
        }else{
            sendPaymentsReportScreenEvent(UiEvent.ShowSnackBar("List is empty"))
        }
    }

    fun makeExcelForPaymentReport(getUri: (uri: Uri) -> Unit){
        if (paymentsReportList.size>0){
            sendPaymentsReportScreenEvent(UiEvent.ShowProgressBar)
            viewModelScope.launch (Dispatchers.IO){
                useCase.makeExcelForPaymentReportUseCase(
                    fromDate = _fromDateState.value,
                    toDate = _toDateState.value,
                    list = paymentsReportList,
                    getUri = getUri,
                    haveAnyError = {haveAnyError, error ->
                        sendPaymentsReportScreenEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError){
                            sendPaymentsReportScreenEvent(UiEvent.ShowSnackBar(error ?:"There have some problem"))
                        }

                    }
                )
            }
        }else{
            sendPaymentsReportScreenEvent(UiEvent.ShowSnackBar("List is empty"))
        }
    }

    fun getReceiptReport(fromDate: LocalDate, toDate: LocalDate) {
        receiptsReportList.clear()
        val fromDateString =
            "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T00:00:00"
        _fromDateState.value = "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T00:00:00"
        _toDateState.value = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}"

        val url =
            HttpRoutes.BASE_URL + HttpRoutes.RECEIPT_REPORT
        Log.e(TAG, "getSupplierLedgerReport: $url")

        viewModelScope.launch(Dispatchers.IO) {
            useCase.getReceiptReportUseCase(
                url = url,
                dateFrom = fromDateString,
                dateTo = toDateString,
                companyId = commonMemory.companyId.toInt()
            ).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendQueryReceiptsReportScreenEvent(UiEvent.ShowProgressBar)

                    }

                    is GetDataFromRemote.Success -> {
                        sendQueryReceiptsReportScreenEvent(UiEvent.CloseProgressBar)
                        receiptsReportList.addAll(value.data)
                        calculateReceiptReportTotals(value.data)
                        Log.w(TAG, "getReceiptReport: ${value.data}", )
                        sendQueryReceiptsReportScreenEvent(UiEvent.Navigate(AccountScreens.ReceiptsReportScreen.route))

                        //sendQueryReceiptsReportScreenEvent()


                    }

                    is GetDataFromRemote.Failed -> {
                        sendQueryReceiptsReportScreenEvent(UiEvent.CloseProgressBar)
                        sendQueryReceiptsReportScreenEvent(
                            UiEvent.ShowSnackBar(
                                value.error.message ?: "There have some error"
                            )
                        )

                    }
                }
            }
        }
    }

    fun makePdfForReceiptReport(getUri: (uri: Uri) -> Unit){
        if (receiptsReportList.size>0){
            sendReceiptsReportScreenEvent(UiEvent.ShowProgressBar)
            viewModelScope.launch (Dispatchers.IO){
                useCase.makePdfForReceiptReportUseCase(
                    fromDate = _fromDateState.value,
                    toDate = _toDateState.value,
                    list = receiptsReportList,
                    receiptReportListTotal = _receiptReportListTotal.value,
                    getUri = getUri,
                    haveAnyError = {haveAnyError, error ->
                        sendReceiptsReportScreenEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError){
                            sendReceiptsReportScreenEvent(UiEvent.ShowSnackBar(error ?:"There have some problem"))
                        }

                    }
                )
            }
        }else{
            sendReceiptsReportScreenEvent(UiEvent.ShowSnackBar("List is empty"))
        }
    }

    fun makeExcelForReceiptReport(getUri: (uri: Uri) -> Unit){
        if (receiptsReportList.size>0){
            sendReceiptsReportScreenEvent(UiEvent.ShowProgressBar)
            viewModelScope.launch (Dispatchers.IO){
                useCase.makeExcelForReceiptReportUseCase(
                    fromDate = _fromDateState.value,
                    toDate = _toDateState.value,
                    list = receiptsReportList,
                    getUri = getUri,
                    haveAnyError = {haveAnyError, error ->
                        sendReceiptsReportScreenEvent(UiEvent.CloseProgressBar)
                        if (haveAnyError){
                            sendReceiptsReportScreenEvent(UiEvent.ShowSnackBar(error ?:"There have some problem"))
                        }

                    }
                )
            }
        }else{
            sendReceiptsReportScreenEvent(UiEvent.ShowSnackBar("List is empty"))
        }
    }

    private fun calculateReceiptReportTotals(data: List<ReceiptResponse>) {
        var sumOfAmount = 0.0
        data.forEach {
            sumOfAmount += it.amount
        }
        _receiptReportListTotal.value = sumOfAmount
    }
}