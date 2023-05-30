package com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.user_name_use_case

import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository

class ReadUserNameUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke() = dataStoreRepository.readUserName()
}