package com.gulfappdeveloper.projectreport.presentation.screens.login_screen.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginErrorMessageAlertDialog(
    message: String,
    onDismissRequest: () -> Unit
) {

    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            Text(text = message)
        },
        confirmButton = {
            Button(onClick = {onDismissRequest()}) {
                Text(text = "Ok")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginErrorMessageAlertDialogPrev() {
    LoginErrorMessageAlertDialog(message = "User Name or Password entered not correct!") {

    }
}