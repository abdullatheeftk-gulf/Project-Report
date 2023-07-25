package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.purchase_summary_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseSummaryResponse
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class PurchaseSummaryReportExcelUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseSummaryResponse>
    ) = pdfExcelRepository.makeExcelForPurchaseSummaryReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )
}