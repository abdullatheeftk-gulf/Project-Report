package com.gulfappdeveloper.projectreport.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.gulfappdeveloper.projectreport.BuildConfig
import com.gulfappdeveloper.projectreport.data.data_store.DataStoreServiceImpl
import com.gulfappdeveloper.projectreport.data.firebase.FirebaseServiceImpl
import com.gulfappdeveloper.projectreport.data.room.LocalCompanyDataDao
import com.gulfappdeveloper.projectreport.data.room.RoomDatabaseServiceImpl
import com.gulfappdeveloper.projectreport.domain.services.DataStoreService
import com.gulfappdeveloper.projectreport.domain.services.ExcelService
import com.gulfappdeveloper.projectreport.domain.services.FirebaseService
import com.gulfappdeveloper.projectreport.domain.services.PdfService
import com.gulfappdeveloper.projectreport.domain.services.RoomDatabaseService
import com.gulfappdeveloper.projectreport.root.AppRoomDatabase
import com.gulfappdeveloper.projectreport.root.CommonMemory
import com.gulfappdeveloper.projectreport.share.excel.ExcelServiceImpl
import com.gulfappdeveloper.projectreport.share.pdf.PdfServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCommonMemory(): CommonMemory {
        return CommonMemory
    }

    @Provides
    @Singleton
    fun provideDataSoreService(@ApplicationContext context: Context): DataStoreService {
        return DataStoreServiceImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppRoomDatabase::class.java,
            if(BuildConfig.APP_STATUS) "report_project_database" else "report_project_database_single"
        )
            .build()

    @Provides
    @Singleton
    fun provideRoomDao(db: AppRoomDatabase) = db.getLocalCompanyDataDao()

    @Provides
    @Singleton
    fun provideRoomDatabaseService(dao: LocalCompanyDataDao): RoomDatabaseService =
        RoomDatabaseServiceImpl(dao = dao)

    @Provides
    @Singleton
    fun providePdfService(
        @ApplicationContext context: Context,
        commonMemory: CommonMemory
    ): PdfService = PdfServiceImpl(
        context = context,
        commonMemory = commonMemory
    )

    @Provides
    @Singleton
    fun provideExcelService(@ApplicationContext context: Context,commonMemory: CommonMemory): ExcelService =
        ExcelServiceImpl(context = context, commonMemory = commonMemory)

    @Provides
    @Singleton
    fun provideFirebaseFireStoreDb() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseService(fdb: FirebaseFirestore): FirebaseService {
        return FirebaseServiceImpl(fdb)
    }
}
