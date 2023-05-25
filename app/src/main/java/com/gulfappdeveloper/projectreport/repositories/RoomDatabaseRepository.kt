package com.gulfappdeveloper.projectreport.repositories

import com.gulfappdeveloper.projectreport.domain.models.room.LocalCompanyData
import com.gulfappdeveloper.projectreport.domain.services.RoomDatabaseService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomDatabaseRepository @Inject constructor(
    private val roomService: RoomDatabaseService
) {
    suspend fun insertData(localCompanyData: LocalCompanyData){
        roomService.insertCompanyData(companyData = localCompanyData)
    }

    suspend fun getAllCompanyData() = roomService.getAllCompanyData()



}