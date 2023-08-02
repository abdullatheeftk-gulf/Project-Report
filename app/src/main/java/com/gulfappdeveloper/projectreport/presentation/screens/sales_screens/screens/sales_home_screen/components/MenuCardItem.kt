package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.sales_home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuCardItem(
    title: String,
    subtitle: String?=null,
    onClicked: () -> Unit
) {
    Card(
        onClick = onClicked,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(
                start = 8.dp, top = 4.dp, bottom = 4.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column (Modifier.padding(start = 4.dp, top = 4.dp, bottom = 4.dp)){
                Text(
                    text = title,
                    fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                    fontSize = 24.sp,
                )
                if (!subtitle.isNullOrEmpty()) {
                    Text(
                        text = subtitle ,
                        fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    )
                }
            }
            IconButton(onClick = onClicked) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun MenuItemCardPrev() {
    MenuCardItem(title = "Sales Invoice Report", subtitle = "It is sample") {

    }
}*/
