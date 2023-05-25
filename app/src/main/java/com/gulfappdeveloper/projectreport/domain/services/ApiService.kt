package com.gulfappdeveloper.projectreport.domain.services

import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.ip4.SeeIp
import com.gulfappdeveloper.projectreport.domain.models.ledger.GetCustomerForLedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.ledger.LedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.license.LicenseRequestBody
import com.gulfappdeveloper.projectreport.domain.models.license.LicenseResponse
import com.gulfappdeveloper.projectreport.domain.models.login_and_register.CompanyRegisterResponse
import com.gulfappdeveloper.projectreport.domain.models.login_and_register.LoginResponse
import kotlinx.coroutines.flow.Flow

interface ApiService {
    suspend fun getWelcomeMessage(url:String):Flow<GetDataFromRemote<String>>

    suspend fun uniLicenseActivation(
        rioLabKey: String,
        url: String,
        licenseRequestBody: LicenseRequestBody
    ): Flow<GetDataFromRemote<LicenseResponse>>

    suspend fun getIp4Address(url: String):Flow<GetDataFromRemote<SeeIp>>

    suspend fun registerCompany(url:String):Flow<GetDataFromRemote<CompanyRegisterResponse>>

    suspend fun login(url: String):Flow<GetDataFromRemote<LoginResponse>>



    // ledger
    suspend fun getCustomerForLedger(url:String):Flow<GetDataFromRemote<List<GetCustomerForLedgerReportResponse>>>
    suspend fun getCustomerLedgers(url:String):Flow<GetDataFromRemote<LedgerReportResponse>>


    // customer payment

    suspend fun getCustomerPayment(url:String):Flow<GetDataFromRemote<List<CustomerPaymentResponse>>>
}