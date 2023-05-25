package com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.ledger.GetCustomerForLedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.ledger.LedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.navigation.LedgerReportScreens
import com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.screens.ledger_report_screen.util.LedgerReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.ledger_report_screens.screens.query_ledger_screen.util.QueryLedgerScreenEvent
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

private const val TAG = "LedgerReportViewModel"
@HiltViewModel
class LedgerReportViewModel @Inject constructor(
    private val useCase: UseCase,
    private val commonMemory:CommonMemory
):ViewModel() {

    val accountList = mutableStateListOf<GetCustomerForLedgerReportResponse>()

    private val _selectedAccount:MutableState<GetCustomerForLedgerReportResponse?> = mutableStateOf(null)
    val selectedAccount:State<GetCustomerForLedgerReportResponse?> = _selectedAccount

    fun setSelectedAccount(value:GetCustomerForLedgerReportResponse){
        _selectedAccount.value = value
    }

    private val _queryLedgerScreenEvent = Channel<QueryLedgerScreenEvent>()
    val queryLedgerScreenEvent = _queryLedgerScreenEvent.receiveAsFlow()

    private fun sendQueryLedgerScreenEvent(event: UiEvent) {
        viewModelScope.launch {
            _queryLedgerScreenEvent.send(QueryLedgerScreenEvent(event))
        }
    }

    private val _ledgerReportScreenEvent = Channel<LedgerReportScreenEvent>()
    val ledgerReportScreenEvent = _ledgerReportScreenEvent.receiveAsFlow()

    private fun sendLedgerReportScreenEvent(event: UiEvent) {
        viewModelScope.launch {
            _ledgerReportScreenEvent.send(LedgerReportScreenEvent(event))
        }
    }

    val ledgerList = mutableStateListOf<LedgerDetail>()

    private val _partyName = mutableStateOf("")
    val partyName:State<String> = _partyName

    private val _balance = mutableStateOf(0f)
    val balance:State<Float> = _balance

    private val _fromDateToDisplay = mutableStateOf("")
    val fromDateToDisplay:State<String> = _fromDateToDisplay

    private val _toDateToDisplay = mutableStateOf("")
    val toDateToDisplay:State<String> = _toDateToDisplay

    init {
        getCustomerForLedger("customer")
    }

    fun getCustomerForLedger(selectedAccountType:String){
        val url = HttpRoutes.BASE_URL+HttpRoutes.GET_CUSTOMER_FOR_LEDGER+commonMemory.companyId+"/$selectedAccountType"
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getCustomerForLedgerUseCase(url = url).collectLatest {value->
                when(value){
                    is GetDataFromRemote.Success->{
                        sendQueryLedgerScreenEvent(UiEvent.CloseProgressBar)
                        accountList.clear()
                        accountList.addAll(value.data)
                        setSelectedAccount(value.data[0])
                    }
                    is GetDataFromRemote.Failed->{
                        sendQueryLedgerScreenEvent(UiEvent.CloseProgressBar)
                        sendQueryLedgerScreenEvent(UiEvent.ShowSnackBar(value.error.message ?: ""))
                        accountList.clear()
                    }
                    is GetDataFromRemote.Loading->{
                        sendQueryLedgerScreenEvent(UiEvent.ShowProgressBar)
                    }
                }
            }
        }
    }

    fun getLedgerReport(fromDate:LocalDate,toDate: LocalDate){
        if (_selectedAccount.value==null){
            sendQueryLedgerScreenEvent(UiEvent.ShowSnackBar("No Account is selected"))
            return
        }
        val fromDateString = "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T00:00:00"
        _fromDateToDisplay.value = "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T00:00:00"
        _toDateToDisplay.value = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}"
        
        val url = HttpRoutes.BASE_URL+HttpRoutes.LEDGER_REPORT+ fromDateString +"/$toDateString"+"/${_selectedAccount.value?.accountId}"+"/${commonMemory.companyId}"
        
        viewModelScope.launch(Dispatchers.IO) { 
            useCase.getCustomerLedgers(url = url).collectLatest {value ->  
                when(value){
                    is GetDataFromRemote.Loading->{
                        sendQueryLedgerScreenEvent(UiEvent.ShowProgressBar)
                    }
                    is GetDataFromRemote.Success->{
                        _partyName.value = value.data.partyName
                        _balance.value = value.data.balance
                        ledgerList.clear()
                        ledgerList.addAll(value.data.details)
                        sendQueryLedgerScreenEvent(UiEvent.CloseProgressBar)
                        Log.d(TAG, "getLedgerReport: ${value.data}")
                        sendQueryLedgerScreenEvent(UiEvent.Navigate(LedgerReportScreens.LedgerReportScreen.route))
                    }
                    is GetDataFromRemote.Failed->{
                        sendQueryLedgerScreenEvent(UiEvent.CloseProgressBar)
                        sendQueryLedgerScreenEvent(UiEvent.ShowSnackBar("failed"))
                        Log.e(TAG, "getLedgerReport: ${value.error}", )

                    }
                }
            }
        }
    }

}

