package com.gulfappdeveloper.projectreport.presentation.screens.main_screen.componenets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItem(
    onClickMenuItem:()->Unit,
    containerColor: Color,
    contentColor: Color,
    menuText:String
) {

    ElevatedCard(
        onClick = onClickMenuItem,
        shape = RoundedCornerShape(25),
        colors = CardDefaults.elevatedCardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = Modifier
            .height(150.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp,

        )
    )
    {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = menuText,
                fontSize = 18.sp,
                fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    }
}

/*
@Preview
@Composable
fun MenuItemPrev() {
    MenuItem(){}
}*/
