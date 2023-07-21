package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.user_sales_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.sales.UserSalesResponse
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class MakeExcelForUserSalesReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<UserSalesResponse>
    ) = pdfExcelRepository.makeExcelForUserSalesReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )
}