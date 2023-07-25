package com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.screens.change_company_screen.componenets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gulfappdeveloper.projectreport.domain.models.room.LocalCompanyData
import com.gulfappdeveloper.projectreport.presentation.screens.settings_screens.SettingsViewModel

@Composable
fun CompanyItems(
    localCompanyData: LocalCompanyData,
    selectedCompanyId: Int,
    onCheckBoxClicked:(LocalCompanyData)->Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (localCompanyData.id == selectedCompanyId) Color(
                0xFFEF7A7A
            ) else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(intrinsicSize = IntrinsicSize.Max)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = localCompanyData.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = localCompanyData.place,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Checkbox(
                checked = localCompanyData.id == selectedCompanyId,
                onCheckedChange = {
                    if (localCompanyData.id != selectedCompanyId) {
                        onCheckBoxClicked(localCompanyData)
                    }
                }
            )


        }

    }
}

/*
@Preview(showBackground = true)
@Composable
fun CompanyItemsPrev() {
    CompanyItems(
        localCompanyData = LocalCompanyData(
            id = 1,
            name = "Hussa al fahad",
            place = "Riyadh",
            taxId = "545454"
        ), selectedCompanyId = 1
    )
}*/
