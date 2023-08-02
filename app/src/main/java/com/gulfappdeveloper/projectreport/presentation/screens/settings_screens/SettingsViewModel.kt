package com.gulfappdeveloper.projectreport.presentation.screens.settings_screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.projectreport.domain.models.firebase.FirebaseError
import com.gulfappdeveloper.projectreport.domain.models.firebase.FirebaseGeneralData
import com.gulfappdeveloper.projectreport.domain.models.general.Error
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.license.LicenseRequestBody
import com.gulfappdeveloper.projectreport.domain.models.login_and_register.CompanyRegisterResponse
import com.gulfappdeveloper.projectreport.domain.models.room.LocalCompanyData
import com.gulfappdeveloper.projectreport.domain.services.FirebaseService
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.add_company_screen.util.AddCompanyScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.change_company_screen.util.ChangeCompanyScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.change_company_screen.util.LicenseActivationBarEvent
import com.gulfappdeveloper.projectreport.root.AppConstants
import com.gulfappdeveloper.projectreport.root.CommonMemory
import com.gulfappdeveloper.projectreport.root.HttpRoutes
import com.gulfappdeveloper.projectreport.root.sendErrorDataToFirebase
import com.gulfappdeveloper.projectreport.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: UseCase,
    private val commonMemory: CommonMemory,
    private val firebaseService: FirebaseService
) : ViewModel() {

    private val _addCompanyScreenEvent = Channel<AddCompanyScreenEvent>()
    val addCompanyScreenEvent = _addCompanyScreenEvent.receiveAsFlow()

    private val _activationCode = mutableStateOf("")
    val activationCode: State<String> = _activationCode

    fun setActivationCode(value: String) {
        _activationCode.value = value
    }

    private val _showActivationBar = mutableStateOf(false)
    val showActivationBar: State<Boolean> = _showActivationBar

    fun setShowActivationBar(value: Boolean) {
        _showActivationBar.value = value
    }

    private val _deviceId = mutableStateOf("")
    val deviceId: State<String> = _deviceId

    private val _ipAddress = mutableStateOf("")
   // val ipAddress: State<String> = _ipAddress

    private val _appActivationStatus = mutableStateOf(false)
    val appActivationStatus: State<Boolean> = _appActivationStatus

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

    private val _licenseActivationBarEvent = Channel<LicenseActivationBarEvent>()
    val licenseActivationBarEvent = _licenseActivationBarEvent.receiveAsFlow()

    private fun sendLicenseActivationBarEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _licenseActivationBarEvent.send(LicenseActivationBarEvent(uiEvent))
        }
    }

    val localCompanyDataList = mutableStateListOf<LocalCompanyData>()

    private val _selectedCompanyId: MutableState<Int> = mutableStateOf(-1)
    val selectedCompanyId: State<Int> = _selectedCompanyId

    init {
        getAllLocalCompanyDataFromRoom()
        readCompanyDataFromDataStore()
        readActivationStatusFromDataStore()
    }

    private fun readActivationStatusFromDataStore() {
        viewModelScope.launch {
            useCase.readActivationStatusUseCase().collectLatest { activationStatus ->
                _appActivationStatus.value = activationStatus
                if (!activationStatus) {
                    readDeviceIdFromDataStore()
                    readIpAddressFromDataStore()
                }
            }
        }
    }

    private fun Boolean.saveActivationStatusToDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.saveActivationUseCase(this@saveActivationStatusToDataStore)
        }
    }

    private fun readIpAddressFromDataStore() {
        viewModelScope.launch {
            useCase.readIpAddressUseCase().collectLatest { value ->
                _ipAddress.value = value
            }
        }
    }

    private fun readDeviceIdFromDataStore() {
        viewModelScope.launch {
            useCase.readDeviceIdUseCase().collectLatest { value ->
                _deviceId.value = value
            }
        }
    }


    private var _companyId = -1

    private fun getAllLocalCompanyDataFromRoom() {
        viewModelScope.launch {
            useCase.getAllLocalCompanyDataUseCase().collectLatest { value ->
                localCompanyDataList.clear()
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
                        commonMemory.companyName = companyDataResponse.name

                    }
                } catch (_: Exception) {
                }

            }
        }
    }

    fun registerCompany(companyCode: String) {
        val funcName = "RootViewModel."+object{}.javaClass.enclosingMethod?.name+ Date()

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

                        sendAddCompanyScreenEvent(UiEvent.Navigate("pop"))
                    }

                    is GetDataFromRemote.Failed -> {
                        val error = value.error
                        firebaseService.sendErrorDataToFirebase(
                            url = url,
                            error = error,
                            funcName = funcName
                        )
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
            } catch (e: Exception) {
                sendChangeCompanyScreenEvent(UiEvent.ShowSnackBar("This company registered before"))
            }
        }
    }

    fun activateApp() {
        if (_activationCode.value.isEmpty()){
            sendLicenseActivationBarEvent(UiEvent.ShowSnackBar(AppConstants.LICENSE_KEY_IS_NOT_ENTERED))
            return
        }

        if (_deviceId.value.isEmpty()){
            sendLicenseActivationBarEvent(UiEvent.ShowSnackBar("Device id is empty"))
            return
        }
        if (_ipAddress.value.isEmpty()){
            sendLicenseActivationBarEvent(UiEvent.ShowSnackBar("Ip address is empty. Please restart your app"))
            return
        }
        val url ="http://license.riolabz.com/license-repo/public/api/v1/verifyjson"
        val licenseRequestBody = LicenseRequestBody(
            licenseKey = _activationCode.value,
            macId = _deviceId.value,
            ipAddress = _ipAddress.value
        )
        viewModelScope.launch(Dispatchers.IO) {
          useCase.uniLicenseActivationUseCase(
              url=url,
              licenseRequestBody = licenseRequestBody
          ).collectLatest {value ->
              when(value){
                  is  GetDataFromRemote.Loading->{
                      sendLicenseActivationBarEvent(UiEvent.ShowProgressBar)
                  }
                  is GetDataFromRemote.Success->{
                      sendLicenseActivationBarEvent(UiEvent.CloseProgressBar)
                      //Showing message on LicenseActivationBar
                      sendLicenseActivationBarEvent(UiEvent.ShowSnackBar(AppConstants.ACTIVATION_SUCCESS))
                      _appActivationStatus.value = true
                      true.saveActivationStatusToDataStore()
                      setShowActivationBar(false)
                      saveGeneralDataToFirebase()
                  }
                  is GetDataFromRemote.Failed->{
                      val error = value.error
                      url.saveErrorDataToFireBase(error, functionName = "activateApp")
                      sendLicenseActivationBarEvent(UiEvent.CloseProgressBar)
                      val errorMessage = value.error.message?:"There have some error when activating app"
                      sendLicenseActivationBarEvent(UiEvent.ShowSnackBar(errorMessage))
                  }
              }
          }
        }

    }


    private fun saveGeneralDataToFirebase(){
        viewModelScope.launch(Dispatchers.IO) {
            firebaseService.insertGeneralData(
                "GeneralData",
                firebaseGeneralData = FirebaseGeneralData(
                        deviceId = _deviceId.value,
                        ipAddress = _ipAddress.value,
                        uniLicense = _activationCode.value
                )
            )
        }
    }

    private fun String.saveErrorDataToFireBase(error: Error, functionName: String){
        viewModelScope.launch(Dispatchers.IO) {
            firebaseService.insertErrorDataToFireStore(
                collectionName = "ErrorData",
                documentName = "SettingsViewModel-$functionName-${Date()}",
                error = FirebaseError(
                    url = this@saveErrorDataToFireBase,
                    errorCode = error.code,
                    errorMessage = error.message?:"Unknown problem",
                    ipAddress = _ipAddress.value
                )

            )
        }
    }

}