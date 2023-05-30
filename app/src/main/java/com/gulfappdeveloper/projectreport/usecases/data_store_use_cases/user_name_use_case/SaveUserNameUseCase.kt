package com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.user_name_use_case

import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository

class SaveUserNameUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(userName:String){
        dataStoreRepository.saveUserName(userName = userName)
    }
}