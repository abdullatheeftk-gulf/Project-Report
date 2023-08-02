package com.gulfappdeveloper.projectreport.root

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.projectreport.BuildConfig
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.room.LocalCompanyData
import com.gulfappdeveloper.projectreport.domain.services.FirebaseService
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
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class RootViewModel @Inject constructor(
    private val useCase: UseCase,
    private val commonMemory: CommonMemory,
    private val firebaseService: FirebaseService
) : ViewModel() {

    private var _isNavFromSettingToLogin = mutableStateOf(false)
    val isNavFromSettingToLogin: State<Boolean> = _isNavFromSettingToLogin

    fun setNavFromSettingToLogin(value: Boolean) {
        _isNavFromSettingToLogin.value = value
    }

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

    //private var _isInitialLoadingFinished = false
    private var _oneTimeSavedDeviceId = false
    private var _oneTimeSavedIpAddress = false
    private var _publicIpAddress = ""
    private var _companyId = -1

    /*private val _uniLicenseDetails: MutableState<UniLicenseDetails?> = mutableStateOf(null)
    val uniLicenseDetails: State<UniLicenseDetails?> = _uniLicenseDetails*/

    private val _deviceIdState: MutableState<String> = mutableStateOf("")
    // val deviceIdState:State<String> = _deviceIdState

    private val _userNameState = mutableStateOf("")
    val userNameState: State<String> = _userNameState

    fun setUserName(value: String) {
        _userNameState.value = value
    }

    val localCompanyDataList = mutableStateListOf<LocalCompanyData>()

    private val _selectedCompanyId: MutableState<Int> = mutableStateOf(-1)
    val selectedCompanyId: State<Int> = _selectedCompanyId

    private val _selectedStore: MutableState<LocalCompanyData?> = mutableStateOf(null)
    val selectedStore: State<LocalCompanyData?> = _selectedStore

    init {
        readDeviceIdUseCase()
        readIpAddressUseCase()

        //readCompanyData()
        getWelcomeMessage()
        getAllLocalCompanyDataFromRoom()

        checkForPublicIpAddressStatus()


    }


    private fun getAllLocalCompanyDataFromRoom() {
        viewModelScope.launch {
            useCase.getAllLocalCompanyDataUseCase().collectLatest { value ->
                localCompanyDataList.clear()
                localCompanyDataList.addAll(value)
            }
        }
    }

    /*private fun updateOperationCount() {
        viewModelScope.launch {
            useCase.updateOperationCountUseCase()
        }
    }
*/
    /* private fun readOperationCount() {
         viewModelScope.launch {
             useCase.readOperationCountUseCase().collectLatest { count ->
                // Log.i(TAG, "readOperationCount: $count")
             }
         }
     }*/

    private fun saveIpAddressUseCase(ipAddress: String) {
        viewModelScope.launch {
            // Log.e(TAG, "saveIpAddressUseCase: ")
            _publicIpAddress.ifEmpty {
                useCase.saveIpAddressUseCase(ipAddress = ipAddress)
            }
        }
    }

    private fun readIpAddressUseCase() {
        viewModelScope.launch {
            useCase.readIpAddressUseCase().collectLatest { value ->
                _publicIpAddress = value
            }
        }
    }

    fun saveDeviceIdUseCase(deviceId: String) {
        viewModelScope.launch {
            _deviceIdState.value.ifEmpty {

                if (!_oneTimeSavedDeviceId) {
                    _oneTimeSavedDeviceId = true
                    useCase.saveDeviceIdUseCase(deviceId = deviceId)
                }
            }

        }
    }

    private fun readDeviceIdUseCase() {
        viewModelScope.launch {
            useCase.readDeviceIdUseCase().collectLatest { value ->
                _deviceIdState.value = value
                // Log.i(TAG, "readDeviceIdUseCase: $value")
            }
        }
    }

    /*private fun saveUniLicenseUseCase() {
        viewModelScope.launch {

        }
    }*/

    /*private fun readUniLicenseKeyDetails() {
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
                    //Log.d(TAG, "readUniLicenseKeyDetails: test")
                    checkForPublicIpAddressStatus()
                    sendSplashScreenEvent(UiEvent.ShowAlertDialog(""))
                }
            }
        }
    }*/

    fun savePreferredStoreToDataStore(localCompanyData: LocalCompanyData) {
        viewModelScope.launch {
            val companyDataString = Json.encodeToString(localCompanyData)
            useCase.saveCompanyDataUseCase(companyData = companyDataString)
        }
    }

    private fun readCompanyData() {
        viewModelScope.launch {
            useCase.readCompanyDataUseCase().collectLatest { value ->
                // Log.w(TAG, "readCompanyData: $value")
                try {
                    if (value.isNotEmpty() || value.isNotBlank()) {
                        val preferredStore =
                            Json.decodeFromString<LocalCompanyData>(value)
                        _companyId = preferredStore.id
                        _selectedCompanyId.value = preferredStore.id
                        commonMemory.companyId = _companyId.toShort()
                        commonMemory.companyName = preferredStore.name
                        _selectedStore.value = preferredStore
                        // Log.e(TAG, "readCompanyData: $_companyId")

                        if (localCompanyDataList.size > 1 && BuildConfig.APP_STATUS) {
                            sendSplashScreenEvent(UiEvent.Navigate(route = RootNavScreens.SelectAStoreScreen.route))
                        } else {
                            sendSplashScreenEvent(UiEvent.Navigate(route = RootNavScreens.LoginScreen.route))
                        }

                    } else {
                        sendSplashScreenEvent(UiEvent.ShowAlertDialog(AppConstants.COMPANY_NOT_REGISTERED))
                        //sendSplashScreenEvent(UiEvent.Navigate(route = RootNavScreens.RegisterCompanyScreen.route))
                    }
                } catch (e: Exception) {
                    // Log.e(TAG, "readCompanyData: $e")
                }

            }
        }
    }

    private fun getWelcomeMessage() {

        val funcName = "RootViewModel." + object {}.javaClass.enclosingMethod?.name + Date()

        val url = HttpRoutes.BASE_URL + HttpRoutes.WELCOME_MESSAGE
        viewModelScope.launch(Dispatchers.IO) {
            useCase.welcomeMessageUseCase(
                url = url
            ).collectLatest {
                when (it) {
                    is GetDataFromRemote.Loading -> {
                        sendSplashScreenEvent(UiEvent.ShowProgressBar)
                    }

                    is GetDataFromRemote.Success -> {
                        //_isInitialLoadingFinished = true
                        sendSplashScreenEvent(UiEvent.CloseProgressBar)

                        _welcomeMessage.value = "Unipospro"
                        delay(10000L)


                        readCompanyData()
                        readUserNameFromDataStore()
                        // Log.w(TAG, "getWelcomeMessage: ${it.data}")
                    }

                    is GetDataFromRemote.Failed -> {

                        sendSplashScreenEvent(UiEvent.CloseProgressBar)
                        val error = it.error
                        firebaseService.sendErrorDataToFirebase(
                            url = url,
                            error = error,
                            funcName = funcName
                        )
                        _welcomeMessage.value =
                            error.message ?: "Error on loading Data from server"
                    }

                }
            }
        }
    }


    private fun checkForPublicIpAddressStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            (1..3000).forEach {
                if (_publicIpAddress.isNotEmpty() && _publicIpAddress.isNotBlank()) {
                    return@launch
                }
                delay(1000L)
                if (it % 5 == 0) {
                    if (_publicIpAddress.isEmpty()) {
                        getIp4Address()
                    }
                }
            }

        }


    }

    private fun getIp4Address() {
        //Log.d(TAG, "getIp4Address: ")
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


    /*private fun isUniPosLicenseExpired(eDate: String): Boolean {

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
    }*/

    fun registerCompany(companyCode: String) {
        val funcName = "RootViewModel." + object {}.javaClass.enclosingMethod?.name + Date()
        val url = HttpRoutes.BASE_URL + HttpRoutes.REGISTER_COMPANY + "/$companyCode"
        viewModelScope.launch(Dispatchers.IO) {
            useCase.registerCompanyUseCase(url = url).collectLatest { value ->
                when (value) {
                    is GetDataFromRemote.Loading -> {
                        // Log.w(TAG, "registerCompany: ")
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
                        // Log.d(TAG, "registerCompany: $result")
                        savePreferredStoreToDataStore(localCompanyData)
                        _companyId = result.id
                        sendRegisterCompanyScreenEvent(UiEvent.Navigate(RootNavScreens.LoginScreen.route))
                    }

                    is GetDataFromRemote.Failed -> {
                        val error = value.error
                        firebaseService.sendErrorDataToFirebase(
                            url = url,
                            error = error,
                            funcName = funcName
                        )
                        //Log.e(TAG, "registerCompany: ${value.error.code}")
                        sendRegisterCompanyScreenEvent(UiEvent.CloseProgressBar)
                        sendRegisterCompanyScreenEvent(UiEvent.ShowSnackBar("Failed to register"))
                    }
                }

            }
        }
    }

    fun login(userName: String, password: String) {
        val funcName = "RootViewModel." + object {}.javaClass.enclosingMethod?.name + Date()
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
                            //Log.d(TAG, "login: ${value.data}")
                            saveUserNameInDataStore(userName = userName)
                        } else {
                            // Log.i(TAG, "login: ${loginResponse.message}")
                            sendLoginScreenEvent(UiEvent.ShowAlertDialog(loginResponse.message))
                        }
                    }

                    is GetDataFromRemote.Failed -> {
                        val error = value.error
                        firebaseService.sendErrorDataToFirebase(
                            url = url,
                            error = error,
                            funcName = funcName
                        )
                        sendLoginScreenEvent(UiEvent.CloseProgressBar)
                        sendLoginScreenEvent(
                            UiEvent.ShowSnackBar(
                                value.error.message
                                    ?: "There have some error with code ${value.error.code}"
                            )
                        )
                        //Log.e(TAG, "login: ${value.error.code}, ${value.error.message}")
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








