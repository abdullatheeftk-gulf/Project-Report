package com.gulfappdeveloper.projectreport.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gulfappdeveloper.projectreport.domain.services.DataStoreService
import com.gulfappdeveloper.projectreport.root.DataStoreConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreConstants.PREFERENCE_NAME)

class DataStoreServiceImpl(context: Context):DataStoreService {

    private object PreferenceKeys {
        val operationCountKey = intPreferencesKey(name = DataStoreConstants.OPERATION_COUNT_KEY)
        val ipAddressKey = stringPreferencesKey(name = DataStoreConstants.IP_ADDRESS_KEY)
        val uniLicenseKey = stringPreferencesKey(name = DataStoreConstants.UNI_LICENSE_SAVE_KEY)
        val deviceIdKey = stringPreferencesKey(name = DataStoreConstants.DEVICE_ID_KEY)
        val companyDataKey = stringPreferencesKey(name = DataStoreConstants.COMPANY_DATA_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun updateOperationCount() {
        dataStore.edit { preference ->
            val count = preference[PreferenceKeys.operationCountKey] ?: 0
            preference[PreferenceKeys.operationCountKey] = count + 1
        }
    }

    override suspend fun saveIpAddress(ipAddress: String) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.ipAddressKey] = ipAddress
        }
    }

    override suspend fun saveUniLicenseData(uniLicenseString: String) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.uniLicenseKey] = uniLicenseString
        }
    }

    override suspend fun saveDeviceId(deviceId: String) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.deviceIdKey] = deviceId
        }
    }

    override suspend fun saveCompanyData(companyData: String) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.companyDataKey] = companyData
        }
    }

    override fun readOperationCount(): Flow<Int> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }
            .map { preferences ->
                val operationCount = preferences[PreferenceKeys.operationCountKey] ?: 0
                operationCount
            }
    }

    override fun readIpaddress(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }
            .map { preferences ->
                val ipAddress = preferences[PreferenceKeys.ipAddressKey] ?: ""
                ipAddress
            }
    }

    override fun readUniLicenseData(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }
            .map { preferences ->
                val license = preferences[PreferenceKeys.uniLicenseKey] ?: ""
                license
            }
    }

    override fun readDeviceId(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }
            .map { preferences ->
                val deviceId = preferences[PreferenceKeys.deviceIdKey] ?: ""
                deviceId
            }
    }

    override fun readCompanyData(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }
            .map { preferences ->
                val companyData = preferences[PreferenceKeys.companyDataKey] ?: ""
                companyData
            }
    }
}