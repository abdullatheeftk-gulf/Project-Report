package com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.screens.user_sales_report_screens.report_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.UserSalesItemTableCell(
    weight: Float,
    text: String,
    fontSize:TextUnit = 16.sp
) {
    Box(
        modifier = Modifier
            .height(56.dp)
            .weight(weight)
            .border(Dp.Hairline, Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            modifier = Modifier
                .padding(horizontal = 4.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = fontSize
        )
    }
}