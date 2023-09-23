package com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.change_company_screen.componenets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gulfappdeveloper.projectreport.presentation.screen_util.UiEvent
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.SettingsViewModel
import com.gulfappdeveloper.projectreport.root.AppConstants
import com.gulfappdeveloper.projectreport.ui.theme.MySecondaryColor
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LicenceActivationBar(
    settingsViewModel: SettingsViewModel,
    hideKeyboard:()->Unit
) {
    val activationCode by settingsViewModel.activationCode
    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var activationMessage by remember {
        mutableStateOf("Your App is not activated. Required activation from Unipospro to continue")
    }

    val deviceId by settingsViewModel.deviceId

    LaunchedEffect(key1 = true) {
        settingsViewModel.licenseActivationBarEvent.collectLatest { value ->
            when (value.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }

                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }
                is UiEvent.ShowSnackBar->{
                    // It will display activation message
                    activationMessage = value.uiEvent.message
                }

                else -> Unit
            }

        }
    }
    Surface(
        shape = RoundedCornerShape(
            bottomEnd = 24.dp,
            bottomStart = 24.dp
        ),
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, color = Color.LightGray),
        tonalElevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Activate App",
                fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Device Id",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = ":",
                    modifier = Modifier.weight(0.1f)
                )
                SelectionContainer {
                    Text(
                        text = deviceId,
                        modifier = Modifier.weight(2f),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

            }
            OutlinedTextField(
                value = activationCode,
                onValueChange = settingsViewModel::setActivationCode,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "Enter your activation code",
                        modifier = Modifier.alpha(03f)
                    )
                },
                label = {
                    Text(
                        text = "Activation code",
                    )
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard()
                        settingsViewModel.activateApp()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    hideKeyboard()
                    settingsViewModel.activateApp()
                }
            ) {
                if (showProgressBar) {
                    Text(text = "Please wait...")
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 1.dp
                    )
                } else {
                    Text(text = "Activate")
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = activationMessage,
                fontStyle = MaterialTheme.typography.labelMedium.fontStyle,
                color = if (activationMessage==AppConstants.ACTIVATION_SUCCESS) Color.Green else MySecondaryColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.clickable {
                        settingsViewModel.setShowActivationBar(false)
                    }
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}

/*
@Preview
@Composable
fun LicenceActivationBarPrev() {
    LicenceActivationBar()
}*/
