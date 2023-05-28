package com.gulfappdeveloper.projectreport.di

import com.gulfappdeveloper.projectreport.repositories.ApiRepository
import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository
import com.gulfappdeveloper.projectreport.repositories.RoomDatabaseRepository
import com.gulfappdeveloper.projectreport.usecases.UseCase
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
import com.gulfappdeveloper.projectreport.usecases.room_use_case.GetAllLocalCompanyData
import com.gulfappdeveloper.projectreport.usecases.room_use_case.RoomInsertDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUseCase(
        apiRepository: ApiRepository,
        dataStoreRepository: DataStoreRepository,
        roomDatabaseRepository: RoomDatabaseRepository
    ) = UseCase(
        welcomeMessageUseCase = WelcomeMessageUseCase(apiRepository = apiRepository),
        uniLicenseActivationUseCase = UniLicenseActivationUseCase(apiRepository = apiRepository),
        getIP4AddressUseCase = GetIP4AddressUseCase(apiRepository = apiRepository),

        registerCompanyUseCase = RegisterCompanyUseCase(apiRepository = apiRepository),

        loginUseCase = LoginUseCase(apiRepository = apiRepository),


        // Data store repository
        saveIpAddressUseCase = SaveIpAddressUseCase(dataStoreRepository = dataStoreRepository),
        readIpAddressUseCase = ReadIpAddressUseCase(dataStoreRepository = dataStoreRepository),

        saveDeviceIdUseCase = SaveDeviceIdUseCase(dataStoreRepository = dataStoreRepository),
        readDeviceIdUseCase = ReadDeviceIdUseCase(dataStoreRepository = dataStoreRepository),

        updateOperationCountUseCase = UpdateOperationCountUseCase(dataStoreRepository = dataStoreRepository),
        readOperationCountUseCase = ReadOperationCountUseCase(dataStoreRepository = dataStoreRepository),

        saveUniLicenseUseCase = SaveUniLicenseUseCase(dataStoreRepository = dataStoreRepository),
        readUniLicenseUseCase = ReadUniLicenseUseCase(dataStoreRepository = dataStoreRepository),

        saveCompanyDataUseCase = SaveCompanyDataUseCase(dataStoreRepository = dataStoreRepository),
        readCompanyDataUseCase = ReadCompanyDataUseCase(dataStoreRepository = dataStoreRepository),

        roomInsertDataUseCase = RoomInsertDataUseCase(roomDatabaseRepository = roomDatabaseRepository),
        getAllLocalCompanyData = GetAllLocalCompanyData(roomDatabaseRepository = roomDatabaseRepository),

        getCustomerForLedgerUseCase = GetCustomerForLedgerUseCase(apiRepository = apiRepository),
        getCustomerLedgers = GetCustomerLedgers(apiRepository = apiRepository),

        getCustomerPaymentUseCase = GetCustomerPaymentUseCase(apiRepository = apiRepository)
    )
}