package com.gulfappdeveloper.projectreport.presentation.screen_util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.TableCell(
    weight: Float,
    text: String,
    textColor:Color
) {
        Text(
            text = text,
            modifier = Modifier
                //.border(0.5.dp, color = Color(0xFF353232))
                .weight(weight)
                .padding(4.dp),
            color = textColor
        )

}