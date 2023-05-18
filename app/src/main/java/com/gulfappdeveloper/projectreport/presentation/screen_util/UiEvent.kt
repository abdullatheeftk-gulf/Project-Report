package com.gulfappdeveloper.projectreport.presentation.screen_util

sealed class UiEvent{
    object ShowProgressBar : UiEvent()
    object CloseProgressBar : UiEvent()
    data class ShowAlertDialog(val message:String) : UiEvent()
    data class ShowToastMessage(val message: String) : UiEvent()
    data class ShowEmptyList(val value: Boolean) : UiEvent()
    data class ShowSnackBar(val message: String, val action: (() -> Unit)? = null) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object ShowButton1 : UiEvent()
    object ShowPleaseWaitText:UiEvent()
}
