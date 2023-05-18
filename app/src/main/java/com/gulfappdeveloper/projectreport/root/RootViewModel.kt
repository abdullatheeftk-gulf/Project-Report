package com.gulfappdeveloper.projectreport.root

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.usecases.api_usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RootViewModel"
@HiltViewModel
class RootViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _splashScreenEvent = Channel<SplashScreenEvent>()
    val splashScreenEvent = _splashScreenEvent.receiveAsFlow()

    private fun sendSplashScreenEvent(event: UiEvent) {
        viewModelScope.launch {
            _splashScreenEvent.send(SplashScreenEvent(event))
        }
    }

    private val _welcomeMessage = mutableStateOf("")
    val welcomeMessage:State<String> = _welcomeMessage

    init {
        viewModelScope.launch {
            useCase.welcomeMessageUseCase(
                url = HttpRoutes.BASE_URL + HttpRoutes.WELCOME_MESSAGE
            ).collectLatest {
                when (it) {
                    is GetDataFromRemote.Loading -> {
                        _welcomeMessage.value = "Loading..."
                    }

                    is GetDataFromRemote.Success -> {
                        _welcomeMessage.value = it.data
                    }

                    is GetDataFromRemote.Failed -> {
                        _welcomeMessage.value = it.error.message!!
                    }

                }
            }
        }
    }
}