package com.gulfappdeveloper.projectreport.presentation.screens.splash_screen.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CompanyNotRegisteredAlert( onDismissRequest: () -> Unit,) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text(text = "OK")
            }
        },
        title = {
            Text(text = "Registration Required")
        },
        text = {
            Text(text = "Your company is not registered")
        },
        shape = RoundedCornerShape(4)
    )
}