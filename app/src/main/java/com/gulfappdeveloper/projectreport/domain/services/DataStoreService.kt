package com.gulfappdeveloper.projectreport.domain.services

import kotlinx.coroutines.flow.Flow

interface DataStoreService {
    suspend fun updateOperationCount()
    suspend fun saveIpAddress(ipAddress: String)
    suspend fun saveUniLicenseData(uniLicenseString: String)
    suspend fun saveDeviceId(deviceId:String)
    suspend fun saveCompanyData(companyData:String)
    suspend fun saveUserName(userName:String)

    fun readOperationCount(): Flow<Int>
    fun readIpaddress(): Flow<String>
    fun readUniLicenseData(): Flow<String>
    fun readDeviceId(): Flow<String>
    fun readCompanyData():Flow<String>
    fun readUserName():Flow<String>
}