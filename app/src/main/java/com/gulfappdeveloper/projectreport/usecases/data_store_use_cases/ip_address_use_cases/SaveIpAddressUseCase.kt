package com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.ip_address_use_cases

import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository

class SaveIpAddressUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(ipAddress: String) {
        dataStoreRepository.saveIpAddress(ipAddress = ipAddress)
    }
}