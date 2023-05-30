package com.gulfappdeveloper.projectreport.usecases

import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.customer_payment.GetCustomerPaymentUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.ledger.GetCustomerForLedgerUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.ledger.GetCustomerLedgers
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.license.GetIP4AddressUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.license.UniLicenseActivationUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.register_and_login_use_cases.LoginUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.register_and_login_use_cases.RegisterCompanyUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.welcome.WelcomeMessageUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.company_data_use_case.ReadCompanyDataUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.company_data_use_case.SaveCompanyDataUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.device_id_use_cases.ReadDeviceIdUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.device_id_use_cases.SaveDeviceIdUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.ip_address_use_cases.ReadIpAddressUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.ip_address_use_cases.SaveIpAddressUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.operation_counter_use_cases.ReadOperationCountUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.operation_counter_use_cases.UpdateOperationCountUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.uni_license_use_cases.ReadUniLicenseUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.uni_license_use_cases.SaveUniLicenseUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.user_name_use_case.ReadUserNameUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.user_name_use_case.SaveUserNameUseCase
import com.gulfappdeveloper.projectreport.usecases.room_use_case.GetAllLocalCompanyData
import com.gulfappdeveloper.projectreport.usecases.room_use_case.RoomInsertDataUseCase

data class UseCase(

    val welcomeMessageUseCase: WelcomeMessageUseCase,

    val uniLicenseActivationUseCase: UniLicenseActivationUseCase,

    val getIP4AddressUseCase: GetIP4AddressUseCase,

    val registerCompanyUseCase: RegisterCompanyUseCase,

    val loginUseCase: LoginUseCase,






    // Data store use cases
    val saveIpAddressUseCase: SaveIpAddressUseCase,
    val readIpAddressUseCase: ReadIpAddressUseCase,

    val saveDeviceIdUseCase: SaveDeviceIdUseCase,
    val readDeviceIdUseCase: ReadDeviceIdUseCase,

    val updateOperationCountUseCase: UpdateOperationCountUseCase,
    val readOperationCountUseCase: ReadOperationCountUseCase,

    val saveUniLicenseUseCase: SaveUniLicenseUseCase,
    val readUniLicenseUseCase: ReadUniLicenseUseCase,

    val saveCompanyDataUseCase: SaveCompanyDataUseCase,
    val readCompanyDataUseCase: ReadCompanyDataUseCase,

    val saveUserNameUseCase: SaveUserNameUseCase,
    val readUserNameUseCase: ReadUserNameUseCase,


    // Room
    val roomInsertDataUseCase: RoomInsertDataUseCase,
    val getAllLocalCompanyData: GetAllLocalCompanyData,

    // ledger
    val getCustomerForLedgerUseCase: GetCustomerForLedgerUseCase,
    val getCustomerLedgers: GetCustomerLedgers,

    // customer payment
    val getCustomerPaymentUseCase: GetCustomerPaymentUseCase


)