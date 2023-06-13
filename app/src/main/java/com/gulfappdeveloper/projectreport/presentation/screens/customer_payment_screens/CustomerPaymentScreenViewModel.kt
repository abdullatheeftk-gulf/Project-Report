/*
package com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.ledger.GetCustomerForLedgerReportResponse
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens.navigation.CustomerPaymentScreens
import com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens.screens.customer_payment_report_screen.util.CustomerPaymentReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.customer_payment_screens.screens.query_customer_payment_screen.util.QueryCustomerPaymentScreenEvent
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

private const val TAG = "CustomerPaymentScreenVi"

@HiltViewModel
class CustomerPaymentScreenViewModel @Inject constructor(
    private val useCase: UseCase,
    private val commonMemory:CommonMemory
) :ViewModel(){

    private val _queryCustomerPaymentScreenEvent = Channel<QueryCustomerPaymentScreenEvent>()
    val queryCustomerPaymentScreenEvent = _queryCustomerPaymentScreenEvent.receiveAsFlow()

    private fun sendQueryCustomerPaymentScreenEvent(event: UiEvent) {
        viewModelScope.launch {
            _queryCustomerPaymentScreenEvent.send(QueryCustomerPaymentScreenEvent(event))
        }
    }

    private val _customerPaymentReportScreenEvent = Channel<CustomerPaymentReportScreenEvent>()
    val customerPaymentReportScreenEvent = _customerPaymentReportScreenEvent.receiveAsFlow()

    private fun sendCustomerPaymentReportScreenEvent(event: UiEvent) {
        viewModelScope.launch {
            _customerPaymentReportScreenEvent.send(CustomerPaymentReportScreenEvent(event))
        }
    }

    val customerPaymentReportList = mutableStateListOf<CustomerPaymentResponse>()


    private val _fromDateState = mutableStateOf("")
    val fromDateState: State<String> = _fromDateState

    private val _toDateState = mutableStateOf("")
    val toDateState: State<String> = _toDateState

    private val _orientation = mutableStateOf(true)
    val orientation:State<Boolean> = _orientation

    fun setOrientation(value:Boolean){
        _orientation.value = value
    }

    fun getCustomerPaymentReport(fromDate:LocalDate,toDate:LocalDate){
        _fromDateState.value = fromDate.toString()
        _toDateState.value = toDate.toString()
        val fromDateString = "${fromDate.year}-${fromDate.monthValue}-${fromDate.dayOfMonth}T00:00:00"

        val toDateString = "${toDate.year}-${toDate.monthValue}-${toDate.dayOfMonth}T00:00:00"
        val url = HttpRoutes.BASE_URL+HttpRoutes.GET_CUSTOMER_PAYMENT_REPORT+ fromDateString +"/$toDateString"+"/${commonMemory.companyId}"
        Log.d(TAG, "getCustomerPaymentReport: $url")
        
        viewModelScope.launch(Dispatchers.IO) { 
            useCase.getCustomerPaymentUseCase(url = url).collectLatest {value ->  
                when(value){
                    is GetDataFromRemote.Loading->{
                        sendQueryCustomerPaymentScreenEvent(UiEvent.ShowProgressBar)

                    }
                    is GetDataFromRemote.Success->{
                        Log.d(TAG, "getCustomerPaymentReport: ${value.data}")
                        sendQueryCustomerPaymentScreenEvent(UiEvent.CloseProgressBar)
                        customerPaymentReportList.clear()
                        customerPaymentReportList.addAll(value.data)
                        sendQueryCustomerPaymentScreenEvent(UiEvent.Navigate(CustomerPaymentScreens.CustomerPaymentReportScreen.route))

                    }
                    is GetDataFromRemote.Failed->{
                        sendQueryCustomerPaymentScreenEvent(UiEvent.CloseProgressBar)
                        Log.e(TAG, "getCustomerPaymentReport: ${value.error}", )
                        customerPaymentReportList.clear()
                        sendQueryCustomerPaymentScreenEvent(UiEvent.ShowSnackBar(value.error.message!!))
                    }
                }
            }
        }
    }
}*/
