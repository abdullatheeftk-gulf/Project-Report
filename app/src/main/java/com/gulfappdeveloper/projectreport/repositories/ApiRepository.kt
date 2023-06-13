package com.gulfappdeveloper.projectreport.repositories

import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.ip4.SeeIp
import com.gulfappdeveloper.projectreport.domain.models.license.LicenseRequestBody
import com.gulfappdeveloper.projectreport.domain.models.license.LicenseResponse
import com.gulfappdeveloper.projectreport.domain.models.login_and_register.CompanyRegisterResponse
import com.gulfappdeveloper.projectreport.domain.services.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getWelcomeMessage(url: String): Flow<GetDataFromRemote<String>> {
        return apiService.getWelcomeMessage(url = url)
    }

    suspend fun uniLicenseActivation(
        rioLabKey: String,
        url: String,
        licenseRequestBody: LicenseRequestBody
    ): Flow<GetDataFromRemote<LicenseResponse>> {
        return apiService.uniLicenseActivation(
            rioLabKey = rioLabKey,
            url = url,
            licenseRequestBody = licenseRequestBody
        )
    }

    suspend fun getIp4Address(url: String): Flow<GetDataFromRemote<SeeIp>> {
        return apiService.getIp4Address(url = url)
    }

    suspend fun registerCompany(url: String):Flow<GetDataFromRemote<CompanyRegisterResponse>>{
        return apiService.registerCompany(url = url)
    }

    suspend fun login(url:String) = apiService.login(url = url)

    suspend fun getCustomerForLedger(url: String) = apiService.getCustomerForLedger(url = url)
    suspend fun getCustomerLedgers(url: String) = apiService.getCustomerLedgers(url = url)

    suspend fun getCustomerPaymentReport(url:String) = apiService.getCustomerPayment(url = url)

    suspend fun getSalesInvoiceReport(url: String) = apiService.getSalesInvoiceReport(url = url)
    suspend fun getSaleSummariesReport(url:String) = apiService.getSaleSummariesReport(url = url)
    suspend fun getUserSalesReport(url: String) = apiService.getUserSalesReport(url = url)

}