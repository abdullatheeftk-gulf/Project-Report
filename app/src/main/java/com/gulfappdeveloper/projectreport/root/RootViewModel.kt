package com.gulfappdeveloper.projectreport.root

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.license.UniLicenseDetails
import com.gulfappdeveloper.projectreport.domain.models.login_and_register.CompanyRegisterResponse
import com.gulfappdeveloper.projectreport.domain.models.room.LocalCompanyData
import com.gulfappdeveloper.projectreport.navigation.RootNavScreens
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.login_screen.util.LoginScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.register_company_screen.util.RegisterCompanyScreenEvent
import com.gulfappdeveloper.projectreport.presentation.screens.splash_screen.util.SplashScreenEvent
import com.gulfappdeveloper.projectreport.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val TAG = "RootViewModel"

@HiltViewModel
class RootViewModel @Inject constructor(
    private val useCase: UseCase,
    private val commonMemory: CommonMemory
) : ViewModel() {

    private val _splashScreenEvent = Channel<SplashScreenEvent>()
    val splashScreenEvent = _splashScreenEvent.receiveAsFlow()

    private fun sendSplashScreenEvent(event: UiEvent) {
        viewModelScope.launch {
            _splashScreenEvent.send(SplashScreenEvent(event))
        }
    }

    private val _registerCompanyScreenEvent = Channel<RegisterCompanyScreenEvent>()
    val registerCompanyScreenEvent = _registerCompanyScreenEvent.receiveAsFlow()

    private fun sendRegisterCompanyScreenEvent(event: UiEvent) {
        viewModelScope.launch {
            _registerCompanyScreenEvent.send(RegisterCompanyScreenEvent(event))
        }
    }

    private val _loginScreenEvent = Channel<LoginScreenEvent>()
    val loginScreenEvent = _loginScreenEvent.receiveAsFlow()

    private fun sendLoginScreenEvent(event: UiEvent) {
        viewModelScope.launch {
            _loginScreenEvent.send(LoginScreenEvent(event))
        }
    }


    private val _welcomeMessage = mutableStateOf("")
    val welcomeMessage: State<String> = _welcomeMessage

    private var _isInitialLoadingFinished = false
    private var _oneTimeSavedDeviceId = false
    private var _oneTimeSavedIpAddress = false
    private var _publicIpAddress = ""
    private var _companyId = -1

    private val _uniLicenseDetails: MutableState<UniLicenseDetails?> = mutableStateOf(null)
    val uniLicenseDetails: State<UniLicenseDetails?> = _uniLicenseDetails

    private val _deviceIdState: MutableState<String> = mutableStateOf("")
    // val deviceIdState:State<String> = _deviceIdState

    private val _userNameState = mutableStateOf("")
    val userNameState: State<String> = _userNameState

    fun setUserName(value:String){
        _userNameState.value = value
    }

    init {
        // updateOperationCount()
        // readUniLicenseKeyDetails()
        // readOperationCount()
        // readIpAddressUseCase()
        // readDeviceIdUseCase()
        //readCompanyData()
        getWelcomeMessage()

    }

    private fun updateOperationCount() {
        viewModelScope.launch {
            Log.e(TAG, "updateOperationCount: ")
            useCase.updateOperationCountUseCase()
        }
    }

    private fun readOperationCount() {
        viewModelScope.launch {
            useCase.readOperationCountUseCase().collectLatest { count ->
                Log.i(TAG, "readOperationCount: $count")
            }
        }
    }

    private fun saveIpAddressUseCase(ipAddress: String) {
        viewModelScope.launch {
            Log.e(TAG, "saveIpAddressUseCase: ")
            useCase.saveIpAddressUseCase(ipAddress = ipAddress)
        }
    }

    private fun readIpAddressUseCase() {
        viewModelScope.launch {
            useCase.readIpAddressUseCase().collectLatest { value ->
                Log.i(TAG, "readIpAddressUseCase: $value")
                _publicIpAddress = value
            }
        }
    }

    fun saveDeviceIdUseCase(deviceId: String) {
        viewModelScope.launch {
            _deviceIdState.value.ifEmpty {

                if (!_oneTimeSavedDeviceId) {
                    _oneTimeSavedDeviceId = true
                    Log.e(TAG, "saveDeviceIdUseCase: ")
                    useCase.saveDeviceIdUseCase(deviceId = deviceId)

                }
            }

        }
    }

    private fun readDeviceIdUseCase() {
        viewModelScope.launch {
            useCase.readDeviceIdUseCase().collectLatest { value ->
                _deviceIdState.value = value
                Log.i(TAG, "readDeviceIdUseCase: $value")
            }
        }
    }

    private fun saveUniLicenseUseCase() {
        viewModelScope.launch {

        }
    }

    private fun readUniLicenseKeyDetails() {
        viewModelScope.launch {
            useCase.readUniLicenseUseCase().collectLatest { value ->
                // checking for saved license ledgerDetails
                if (value.isNotEmpty() && value.isNotBlank()) {

                    val licenseDetails = Json.decodeFromString<UniLicenseDetails>(value)

                    _uniLicenseDetails.value = licenseDetails

                    // check saved license is demo
                    if (licenseDetails.licenseType == "demo" && !licenseDetails.expiryDate.isNullOrBlank() && licenseDetails.expiryDate.isNotEmpty()) {

                        // check for license expired
                        if (isUniPosLicenseExpired(licenseDetails.expiryDate)) {
                            checkForPublicIpAddressStatus()
                            sendSplashScreenEvent(UiEvent.ShowAlertDialog("License_Expired"))
                            // demo license expired


                        } else {
                            // demo license not expired
                            if (!_isInitialLoadingFinished) {
                                getWelcomeMessage()
                            }
                        }
                    }
                    if (licenseDetails.licenseType == "permanent") {
                        // license is permanent
                        if (!_isInitialLoadingFinished) {
                            getWelcomeMessage()
                        }

                    }
                } else {
                    Log.d(TAG, "readUniLicenseKeyDetails: test")
                    checkForPublicIpAddressStatus()
                    sendSplashScreenEvent(UiEvent.ShowAlertDialog(""))
                }
            }
        }
    }

    private fun saveCompanyData(companyRegisterResponse: CompanyRegisterResponse) {
        viewModelScope.launch {
            val companyDataString = Json.encodeToString(companyRegisterResponse)
            useCase.saveCompanyDataUseCase(companyData = companyDataString)
        }
    }

    private fun readCompanyData() {
        viewModelScope.launch {
            useCase.readCompanyDataUseCase().collectLatest { value ->
                // Log.w(TAG, "readCompanyData: $value")
                try {
                    if (value.isNotEmpty() || value.isNotBlank()) {
                        val companyDataResponse =
                            Json.decodeFromString<CompanyRegisterResponse>(value)
                        _companyId = companyDataResponse.id
                        commonMemory.companyId = _companyId.toShort()
                        commonMemory.companyName = companyDataResponse.name
                        // Log.e(TAG, "readCompanyData: $_companyId")
                        sendSplashScreenEvent(UiEvent.Navigate(route = RootNavScreens.LoginScreen.route))

                    } else {
                        sendSplashScreenEvent(UiEvent.ShowAlertDialog(AppConstants.COMPANY_NOT_REGISTERED))
                        //sendSplashScreenEvent(UiEvent.Navigate(route = RootNavScreens.RegisterCompanyScreen.route))
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "readCompanyData: $e")
                }

            }
        }
    }

    private fun getWelcomeMessage() {
        // Log.i(TAG, "getWelcomeMessage: ")
        viewModelScope.launch(Dispatchers.IO) {
            useCase.welcomeMessageUseCase(
                url = HttpRoutes.BASE_URL + HttpRoutes.WELCOME_MESSAGE
            ).collectLatest {
                when (it) {
                    is GetDataFromRemote.Loading -> {
                        sendSplashScreenEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {
                        //_isInitialLoadingFinished = true
                        sendSplashScreenEvent(UiEvent.CloseProgressBar)

                        _welcomeMessage.value = "Unipospro"
                        delay(2000L)


                        readCompanyData()
                        readUserNameFromDataStore()
                        // Log.w(TAG, "getWelcomeMessage: ${it.data}")
                    }

                    is GetDataFromRemote.Failed -> {
                        sendSplashScreenEvent(UiEvent.CloseProgressBar)
                        Log.e(TAG, "getWelcomeMessage: ${it.error}")
                        _welcomeMessage.value = "Error on Loading data from server"
                    }

                }
            }
        }
    }


    private fun checkForPublicIpAddressStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            (1..180).forEach {
                if (_publicIpAddress.isNotEmpty() && _publicIpAddress.isNotBlank()) {
                    sendSplashScreenEvent(
                        UiEvent.Navigate(RootNavScreens.UniLicenseActivationScreen.route)
                    )
                    return@launch
                }
                delay(1000L)
                if (it % 5 == 0) {
                    getIp4Address()
                }
                if (it == 180) {
                    sendSplashScreenEvent(
                        UiEvent.ShowSnackBar("There have some error on reading Public Ip address. Please restart application")
                    )
                    sendSplashScreenEvent(UiEvent.CloseProgressBar)
                    return@launch
                }
            }

        }


    }

    private fun getIp4Address() {
        Log.d(TAG, "getIp4Address: ")
        val url = HttpRoutes.SEE_IP4
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getIP4AddressUseCase(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Success -> {
                        _publicIpAddress = value.data.ip ?: ""
                        value.data.ip?.let {
                            if (it.isNotEmpty() || it.isNotBlank()) {
                                if (!_oneTimeSavedIpAddress) {
                                    _oneTimeSavedIpAddress = true
                                    saveIpAddressUseCase(ipAddress = it)
                                }
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
    }


    private fun isUniPosLicenseExpired(eDate: String): Boolean {

        return try {
            val expDate: Date = SimpleDateFormat(
                "dd-MM-yyyy",
                Locale.getDefault()
            ).parse(eDate)!!

            val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date())
            val month = SimpleDateFormat("MM", Locale.getDefault()).format(Date())
            val day = SimpleDateFormat("dd", Locale.getDefault()).format(Date())

            val currentDate =
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("${day}-${month}-${year}")


            val comparison = currentDate?.after(expDate)!!


            comparison
        } catch (e: Exception) {
            true
        }
    }

    fun registerCompany(companyCode: String) {
        val url = HttpRoutes.BASE_URL + HttpRoutes.REGISTER_COMPANY + "/$companyCode"
        viewModelScope.launch(Dispatchers.IO) {
            useCase.registerCompanyUseCase(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        Log.w(TAG, "registerCompany: ")
                        sendRegisterCompanyScreenEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {

                        sendRegisterCompanyScreenEvent(UiEvent.CloseProgressBar)
                        val result = value.data
                        val localCompanyData = LocalCompanyData(
                            id = result.id,
                            name = result.name,
                            place = result.place,
                            taxId = result.taxId
                        )
                        insertCompanyDataToLocalDatabase(localCompanyData = localCompanyData)
                        Log.d(TAG, "registerCompany: $result")
                        saveCompanyData(companyRegisterResponse = result)
                        _companyId = result.id
                        sendRegisterCompanyScreenEvent(UiEvent.Navigate(RootNavScreens.LoginScreen.route))
                    }

                    is GetDataFromRemote.Failed -> {
                        Log.e(TAG, "registerCompany: ${value.error.code}")
                        sendRegisterCompanyScreenEvent(UiEvent.CloseProgressBar)
                        sendRegisterCompanyScreenEvent(UiEvent.ShowSnackBar("Failed to register"))
                    }
                }

            }
        }
    }

    fun login(userName: String, password: String) {
        if (_companyId == -1) {
            sendLoginScreenEvent(UiEvent.ShowSnackBar("Company is not registered"))
            return
        }
        val url = HttpRoutes.BASE_URL + HttpRoutes.LOGIN + "/$userName/$password/$_companyId"
        viewModelScope.launch(Dispatchers.IO) {
            useCase.loginUseCase(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        sendLoginScreenEvent(UiEvent.ShowProgressBar)
                        //Log.w(TAG, "login: ")
                    }

                    is GetDataFromRemote.Success -> {
                        sendLoginScreenEvent(UiEvent.CloseProgressBar)
                        val loginResponse = value.data
                        if (loginResponse.status) {
                            sendLoginScreenEvent(UiEvent.Navigate(RootNavScreens.MainScreen.route))
                            commonMemory.userId = value.data.userId?.toShort()!!
                            Log.d(TAG, "login: ${value.data}")
                            saveUserNameInDataStore(userName = userName)
                        }else{
                            Log.i(TAG, "login: ${loginResponse.message}")
                            sendLoginScreenEvent(UiEvent.ShowAlertDialog(loginResponse.message))
                        }
                    }

                    is GetDataFromRemote.Failed -> {
                        sendLoginScreenEvent(UiEvent.CloseProgressBar)
                        sendLoginScreenEvent(UiEvent.Navigate("Error"))
                        Log.e(TAG, "login: ${value.error.code}, ${value.error.message}",)
                    }

                }
            }
        }
    }

    private fun insertCompanyDataToLocalDatabase(localCompanyData: LocalCompanyData) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.roomInsertDataUseCase(
                localCompanyData = localCompanyData
            )
        }
    }

    private fun saveUserNameInDataStore(userName: String) {
        viewModelScope.launch {
            useCase.saveUserNameUseCase(userName)
        }
    }

    private fun readUserNameFromDataStore() {
        viewModelScope.launch {
            useCase.readUserNameUseCase().collectLatest { value ->
                _userNameState.value = value
            }
        }
    }


}








