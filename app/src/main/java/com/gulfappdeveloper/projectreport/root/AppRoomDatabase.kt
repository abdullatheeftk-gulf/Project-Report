package com.gulfappdeveloper.projectreport.root

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gulfappdeveloper.projectreport.data.room.LocalCompanyDataDao
import com.gulfappdeveloper.projectreport.domain.models.room.LocalCompanyData

@Database(entities = [LocalCompanyData::class], version = 1)
abstract class AppRoomDatabase:RoomDatabase() {
    abstract fun getLocalCompanyDataDao():LocalCompanyDataDao
}