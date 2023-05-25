package com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.ip_address_use_cases

import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadIpAddressUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
     operator fun invoke(): Flow<String> {
        return dataStoreRepository.readIpAddress()
    }
}