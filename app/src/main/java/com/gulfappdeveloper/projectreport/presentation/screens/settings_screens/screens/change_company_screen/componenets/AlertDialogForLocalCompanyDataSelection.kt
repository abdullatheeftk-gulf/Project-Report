package com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.change_company_screen.componenets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.gulfappdeveloper.projectreport.domain.models.room.LocalCompanyData
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.SettingsViewModel

@Composable
fun AlertDialogForLocalCompanyDataSelection(
    localCompanyData: LocalCompanyData,
    onDismissRequest: () -> Unit,
    settingsViewModel: SettingsViewModel
) {
    AlertDialog(
        title = {
            Text(text = "Warning")
        },
        text = {
            Text(text = "You have selected ${localCompanyData.name} for reports. Required login")
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = {
                settingsViewModel.saveCompanyDataToDataStore(localCompanyData = localCompanyData)
                onDismissRequest()
            }) {
                Text(text = "Ok")
            }

        },
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(text = "Cancel")
            }

        },
    )
}