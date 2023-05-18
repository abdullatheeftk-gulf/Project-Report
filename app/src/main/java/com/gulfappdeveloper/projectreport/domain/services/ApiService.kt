package com.gulfappdeveloper.projectreport.domain.services

import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import kotlinx.coroutines.flow.Flow

interface ApiService {
    suspend fun getWelcomeMessage(url:String):Flow<GetDataFromRemote<String>>
}