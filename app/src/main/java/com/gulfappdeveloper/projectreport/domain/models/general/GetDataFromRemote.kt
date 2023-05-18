package com.gulfappdeveloper.projectreport.domain.models.general

sealed class GetDataFromRemote<out T>{
    object Loading: GetDataFromRemote<Nothing>()
    data class Success<T>(val data:T): GetDataFromRemote<T>()
    data class Failed(val error: Error):GetDataFromRemote<Nothing>()

}
