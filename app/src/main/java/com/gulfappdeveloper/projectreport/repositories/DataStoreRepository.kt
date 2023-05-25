package com.gulfappdeveloper.projectreport.repositories

import com.gulfappdeveloper.projectreport.domain.services.DataStoreService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepository @Inject constructor(
    private val dataStoreService: DataStoreService
) {
    suspend fun updateOperationCount() {
        dataStoreService.updateOperationCount()
    }



    suspend fun saveIpAddress(ipAddress: String) {
        dataStoreService.saveIpAddress(ipAddress = ipAddress)
    }


    suspend fun saveUniLicenseKey(uniLicenseString: String) {
        dataStoreService.saveUniLicenseData(uniLicenseString = uniLicenseString)
    }

    suspend fun saveDeviceId(deviceId:String){
        dataStoreService.saveDeviceId(deviceId = deviceId)
    }

    suspend fun saveCompanyData(companyData:String){
        dataStoreService.saveCompanyData(companyData = companyData)
    }



    fun readOperationCount(): Flow<Int> {
        return dataStoreService.readOperationCount()
    }


    fun readIpAddress(): Flow<String> {
        return dataStoreService.readIpaddress()
    }

    fun readUniLicenseKey(): Flow<String> {
        return dataStoreService.readUniLicenseData()
    }

    fun readDeviceId(): Flow<String> {
        return dataStoreService.readDeviceId()
    }

    fun readCompanyData() = dataStoreService.readCompanyData()
}