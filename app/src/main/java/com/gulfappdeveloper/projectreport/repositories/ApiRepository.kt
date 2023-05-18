package com.gulfappdeveloper.projectreport.repositories

import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.services.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getWelcomeMessage(url: String): Flow<GetDataFromRemote<String>> {
        return apiService.getWelcomeMessage(url = url)
    }
}