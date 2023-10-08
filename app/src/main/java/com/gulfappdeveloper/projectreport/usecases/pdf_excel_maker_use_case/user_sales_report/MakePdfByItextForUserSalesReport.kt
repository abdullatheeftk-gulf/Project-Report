package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.user_sales_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.sales.UserSalesResponse
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class MakePdfByItextForUserSalesReport(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<UserSalesResponse>
    ) = pdfExcelRepository.writePdfForUserSalesReportByItext(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )
}