package com.gulfappdeveloper.projectreport.usecases.room_use_case

import com.gulfappdeveloper.projectreport.repositories.RoomDatabaseRepository

class GetAllLocalCompanyData
    (private val roomDatabaseRepository: RoomDatabaseRepository) {
    suspend operator fun invoke() = roomDatabaseRepository.getAllCompanyData()
}