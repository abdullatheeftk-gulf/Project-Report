package com.gulfappdeveloper.projectreport.presentation.screens.settings_screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.login_and_register.CompanyRegisterResponse
import com.gulfappdeveloper.projectreport.domain.models.room.LocalCompanyData
import com.gulfappdeveloper.projectreport.navigation.RootNavScreens
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_invoice_report_screens.report_screen.util.SalesInvoiceReportScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.add_company_screen.util.AddCompanyScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.change_company_screen.util.ChangeCompanyScreenEvent
import com.gulfappdeveloper.projectreport.root.AppConstants
import com.gulfappdeveloper.projectreport.root.CommonMemory
import com.gulfappdeveloper.projectreport.root.HttpRoutes
import com.gulfappdeveloper.projectreport.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

private const val TAG = "SettingsViewModel"

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: UseCase,
    private val commonMemory: CommonMemory
) : ViewModel() {

    private val _addCompanyScreenEvent = Channel<AddCompanyScreenEvent>()
    val addCompanyScreenEvent = _addCompanyScreenEvent.receiveAsFlow()

    private fun sendAddCompanyScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _addCompanyScreenEvent.send(AddCompanyScreenEvent(uiEvent))
        }
    }

    private val _changeCompanyScreenEvent = Channel<ChangeCompanyScreenEvent>()
    val changeCompanyScreenEvent = _changeCompanyScreenEvent.receiveAsFlow()

    private fun sendChangeCompanyScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _changeCompanyScreenEvent.send(ChangeCompanyScreenEvent(uiEvent))
        }
    }
    val localCompanyDataList = mutableStateListOf<LocalCompanyData>()

    private val _selectedCompanyId: MutableState<Int> = mutableStateOf(-1)
    val selectedCompanyId: State<Int> = _selectedCompanyId

    init {
        getAllLocalCompanyDataFromRoom()
        readCompanyDataFromDataStore()
    }


    private var _companyId = -1

    private fun getAllLocalCompanyDataFromRoom() {
        viewModelScope.launch {
            useCase.getAllLocalCompanyDataUseCase().collectLatest { value ->
                localCompanyDataList.clear()
                Log.i(TAG, "getAllLocalCompanyData: $value")
                localCompanyDataList.addAll(value)
            }
        }
    }

     fun saveCompanyDataToDataStore(localCompanyData: LocalCompanyData) {
        viewModelScope.launch(Dispatchers.IO) {
            val companyDataString = Json.encodeToString(localCompanyData)
            useCase.saveCompanyDataUseCase(companyData = companyDataString)
        }
    }

    private fun readCompanyDataFromDataStore() {
        viewModelScope.launch {
            useCase.readCompanyDataUseCase().collectLatest { value ->
                //Log.w(TAG, "readCompanyData: $value")
                try {
                    if (value.isNotEmpty() || value.isNotBlank()) {
                        val companyDataResponse =
                            Json.decodeFromString<CompanyRegisterResponse>(value)
                        _companyId = companyDataResponse.id
                        commonMemory.companyId = _companyId.toShort()
                        _selectedCompanyId.value = companyDataResponse.id
                        Log.e(TAG, "readCompanyDataFromDataStore: data from data store-> $companyDataResponse")
                        commonMemory.companyName = companyDataResponse.name
                        Log.d(TAG, "commonMemory: ${commonMemory.companyId}")
                        // Log.e(TAG, "readCompanyData: $_companyId")
                        //sendSplashScreenEvent(UiEvent.Navigate(route = RootNavScreens.LoginScreen.route))

                    } else {
                        //sendSplashScreenEvent(UiEvent.ShowAlertDialog(AppConstants.COMPANY_NOT_REGISTERED))
                        //sendSplashScreenEvent(UiEvent.Navigate(route = RootNavScreens.RegisterCompanyScreen.route))
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "readCompanyDataFromDataStore: ${e.message}")
                }

            }
        }
    }

    fun registerCompany(companyCode: String) {
        val url = HttpRoutes.BASE_URL + HttpRoutes.REGISTER_COMPANY + "/$companyCode"
        viewModelScope.launch(Dispatchers.IO) {
            useCase.registerCompanyUseCase(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendAddCompanyScreenEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {

                        sendAddCompanyScreenEvent(UiEvent.CloseProgressBar)
                        val result = value.data
                        val localCompanyData = LocalCompanyData(
                            id = result.id,
                            name = result.name,
                            place = result.place,
                            taxId = result.taxId
                        )
                        insertCompanyDataToLocalDatabase(localCompanyData = localCompanyData)
                        //saveCompanyData(companyRegisterResponse = result)

                        sendAddCompanyScreenEvent(UiEvent.Navigate("pop"))
                    }

                    is GetDataFromRemote.Failed -> {
                        sendAddCompanyScreenEvent(UiEvent.CloseProgressBar)
                        sendAddCompanyScreenEvent(UiEvent.ShowSnackBar("Failed to register"))
                    }
                }

            }
        }
    }

    private fun insertCompanyDataToLocalDatabase(localCompanyData: LocalCompanyData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                useCase.roomInsertDataUseCase(
                    localCompanyData = localCompanyData
                )
            }catch (e:Exception){
                sendChangeCompanyScreenEvent(UiEvent.ShowSnackBar("This company registered before"))
                Log.e(TAG, "insertCompanyDataToLocalDatabase: ${e.message}", )
            }
        }
    }

}